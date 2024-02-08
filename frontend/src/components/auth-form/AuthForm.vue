<script setup lang="ts">
	import icons from "@/components/icons";
	import Input from "./Input.vue";
	import SubmitBtn from "./SubmitBtn.vue";
	import { ref, watchEffect, type Ref, watch } from "vue";
	import { APIError } from "@/api/errors";
	type FormStatus = "normal" | "fail" | "success" | "loading";
	type FormAction = "register" | "login";
	type InputStatus = "normal" | "warning" | "error" | "success";
	type InputItems = {
		value: string;
		status: InputStatus;
		message: string;
	};
	const props = defineProps<{
		action: FormAction;
	}>();
	const status: Ref<FormStatus> = ref("normal");
	const action: Ref<FormAction> = ref(props.action);
	const username: Ref<InputItems> = ref({
		value: "",
		status: "normal",
		message: "",
	});
	const password: Ref<InputItems> = ref({
		value: "",
		status: "normal",
		message: "",
	});
	const password2: Ref<InputItems> = ref({
		value: "",
		status: "normal",
		message: "",
	});
	watchEffect(async () => {
		if (status.value === "normal") {
			username.value.status = "normal";
			password.value.status = "normal";
			password2.value.status = "normal";
		} else {
			setTimeout(() => {
				status.value = "normal";
			}, 3000);
		}
	});
	watch(action, () => {
		status.value = "normal";
		username.value.value = "";
		username.value.message = "";
		username.value.status = "normal";
		password.value.value = "";
		password.value.message = "";
		password.value.status = "normal";
		password2.value.value = "";
		password2.value.message = "";
		password2.value.status = "normal";
	});
	function changeAction(a: FormAction): void {
		switch (a) {
			case "login":
				action.value = "login";
				history.pushState({}, "Login", "/auth/login");
				break;
			case "register":
				action.value = "register";
				history.pushState({}, "Register", "/auth/register");
				break;
		}
	}
	function emptyValidator(items: InputItems[]): boolean {
		const errors = [];
		items.forEach((item) => {
			if (item.value.length === 0) {
				errors.push(item);
				inputMessageNotification(
					item,
					"warning",
					"This area cannot be empty."
				);
			}
		});
		if (errors.length > 0) {
			return true;
		} else {
			return false;
		}
	}
	function patternValidator(
		items: { it: InputItems; pattern: RegExp; message: string }[]
	): boolean {
		const errors = [];
		items.forEach((item) => {
			if (!item.pattern.test(item.it.value)) {
				errors.push(item);
				inputMessageNotification(item.it, "warning", item.message);
			}
		});
		if (errors.length > 0) {
			return true;
		} else {
			return false;
		}
	}
	function password2Validator(
		password2: InputItems,
		password: InputItems
	): boolean {
		if (password2.value === password.value) {
			return true;
		} else {
			inputMessageNotification(
				password2,
				"warning",
				"The passwords are inconsistent."
			);
			return false;
		}
	}
	function inputMessageNotification(
		item: InputItems,
		status: InputStatus,
		message: string
	): void {
		item.status = status;
		item.message = message;
	}
	const inputItemPatterns = [
		{
			it: username.value,
			pattern: RegExp("^[a-z][a-z0-9_]{2,49}$"),
			message:
				"Ensures valid usernames: 3-50 characters, starts with a lowercase letter, allows only lowercase letters, numbers, and underscores.",
		},
		{
			it: password.value,
			pattern: RegExp(
				"^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{7,}$"
			),
			message:
				"Enforces secure passwords: at least 7 characters with a mix of uppercase and lowercase letters, numbers, and special symbols.",
		},
	];
	function submitForm(): void {
		status.value = "loading";
		username.value.message = "";
		password.value.message = "";
		switch (action.value) {
			case "login":
				const emptyErrorLogin = emptyValidator([
					username.value,
					password.value,
				]);
				if (emptyErrorLogin) {
					status.value = "fail";
					return;
				}
				const patternErrorLogin = patternValidator(inputItemPatterns);
				if (patternErrorLogin) {
					status.value = "fail";
					return;
				}
				const loginUrl = "http://localhost:5000/v1/account/token";
				const loginFormData = new URLSearchParams();
				loginFormData.append("grant_type", "password");
				loginFormData.append("username", username.value.value);
				loginFormData.append("password", password.value.value);
				fetch(loginUrl, {
					method: "POST",
					body: loginFormData,
					headers: {
						"Content-Type": "application/x-www-form-urlencoded",
					},
				})
					.then((response) => {
						if (response.status === 200) {
							status.value = "success";
							console.log("Success get access token.");
						} else {
							status.value = "fail";
							console.log("Fail to get access token.");
						}
						return response;
					})
					.then((response) => response.json())
					.then((responseData) => {
						if (responseData.error) {
							const error = responseData.error as APIError;
							switch (error.target) {
								case "username":
									inputMessageNotification(
										username.value,
										"error",
										error.message
									);
									return;
								case "password":
									inputMessageNotification(
										password.value,
										"error",
										error.message
									);
									return;
								default:
									return;
							}
						} else {
							console.log(responseData);
							localStorage.setItem(
								"access_token",
								responseData.access_token
							);
							console.log("Successfully login.");
							window.location.href = "/world";
						}
					});
				break;
			case "register":
				const emptyErrorRegister = emptyValidator([
					username.value,
					password.value,
					password2.value,
				]);
				if (emptyErrorRegister) {
					status.value = "fail";
					return;
				}
				const patternErrorRegister =
					patternValidator(inputItemPatterns);
				if (patternErrorRegister) {
					status.value = "fail";
					return;
				} else {
					inputMessageNotification(
						password.value,
						"success",
						"Valid password."
					);
				}
				const password2Error = password2Validator(
					password2.value,
					password.value
				);
				if (!password2Error) {
					status.value = "fail";
					return;
				} else {
					inputMessageNotification(
						password2.value,
						"success",
						"Valid password."
					);
				}
				const registerUrl = "http://localhost:5000/v1/account/users";
				const formData = new URLSearchParams();
				formData.append("username", username.value.value);
				formData.append("password", password.value.value);
				fetch(registerUrl, {
					method: "POST",
					body: formData,
					headers: {
						"Content-Type": "application/x-www-form-urlencoded",
					},
				})
					.then((response) => {
						if (response.status === 201) {
							status.value = "success";
							inputMessageNotification(
								username.value,
								"success",
								"Valid username."
							);
							setTimeout(() => {
								changeAction("login");
							}, 2000);
							console.log("Register success.");
							return;
						} else {
							status.value = "fail";
							return response.json();
						}
					})
					.then((resp) => {
						if (resp) {
							console.log(resp);
							const errorResp = resp.error;
							const error = errorResp as APIError;
							switch (error.target) {
								case "username":
									status.value = "fail";
									inputMessageNotification(
										username.value,
										"error",
										error.message
									);
									return;
								default:
									status.value = "fail";
									return;
							}
						}
					});
				break;
		}
	}
</script>

<template>
	<div class="parent-VkW45HT5Je">
		<div class="switch-action">
			<button
				type="button"
				:class="[
					'sa-btn',
					'login',
					{ active: action === 'login' },
					{ normal: action != 'login' },
				]"
				@click="changeAction('login')"
			>
				<span class="sab-text" v-if="action === 'login'">Login</span>
				<component
					class="icon big"
					:is="icons.login"
					v-if="action != 'login'"
				/>
			</button>
			<button
				type="button"
				:class="[
					'sa-btn',
					'register',
					{ active: action === 'register' },
					{ normal: action != 'register' },
				]"
				@click="changeAction('register')"
			>
				<component
					class="icon big"
					:is="icons.register"
					v-if="action != 'register'"
				/>
				<span class="sab-text" v-if="action === 'register'"
					>Register</span
				>
			</button>
		</div>
		<form @submit.prevent="submitForm">
			<div class="inputs-messages-submitbtn">
				<div class="im-container">
					<div class="imc-items">
						<Input
							input-name="username"
							v-model="username.value"
							:input-status="username.status"
						/>
						<span class="imc-message">
							{{ username.message }}
						</span>
					</div>
					<div class="imc-items">
						<Input
							input-type="password"
							input-name="password"
							v-model="password.value"
							:input-status="password.status"
						/>
						<span class="imc-message">
							{{ password.message }}
						</span>
					</div>
					<div class="imc-items" v-if="action === 'register'">
						<Input
							input-type="password"
							input-name="password2"
							placeholder="Repeat password"
							v-model="password2.value"
							:input-status="password2.status"
						/>
						<span class="imc-message">
							{{ password2.message }}
						</span>
					</div>
				</div>
				<SubmitBtn :btn-type="action" :btn-status="status" />
			</div>
		</form>
	</div>
</template>

<style scoped>
	.parent-VkW45HT5Je {
		width: fit-content;
	}
	form {
		width: fit-content;
		padding: 25px 60px;
		outline: #dddddd solid 1px;
	}
	.inputs-messages-submitbtn {
		display: flex;
		flex-direction: column;
		justify-content: flex-start;
		align-items: center;
	}
	.im-container {
		display: flex;
		flex-direction: column;
		justify-content: flex-start;
		align-items: center;
	}
	.imc-items {
		display: flex;
		flex-direction: column;
		justify-content: flex-start;
		align-items: center;
		margin-bottom: 10px;
	}
	.imc-message {
		width: 260px;
		min-height: 13px;
		margin-top: 2px;
		font-size: 13px;
		line-height: 13px;
		font-weight: lighter;
	}
	.switch-action {
		width: 380px;
		height: 80px;
		display: flex;
		justify-content: space-between;
		align-items: center;
		margin-bottom: 10px;
	}
	.sa-btn {
		height: 100%;
		display: flex;
		justify-content: center;
		align-items: center;
		border: none;
		transition: width 0.5s ease;
	}
	.sa-btn.login {
		background-color: var(--second-color);
	}
	.sa-btn.register {
		background-color: var(--first-color);
	}
	.sa-btn.active {
		width: 320px;
	}
	.sa-btn.normal {
		width: 60px;
	}
	.sab-text {
		font-size: 1.5rem;
		font-weight: 800;
		letter-spacing: 1px;
	}
</style>
