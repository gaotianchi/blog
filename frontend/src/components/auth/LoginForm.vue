<script setup lang="ts">
	import { reactive, ref, watch, type Ref } from "vue";
	import type { AuthStatus, LoginStatus } from "@/typing";
	import { defaultLoginStatus } from "@/defaults";
	import { login } from "@/api/remote";
	import type { APIError } from "@/api/errors";
	import { setAccessToken } from "@/api/local";
	import FormInput from "./AuthFormInput.vue";
	import SubmitButton from "./AuthSubmitButton.vue";
	import AuthFormErrorMessage from "./AuthFormErrorMessage.vue";
	import {
		usernameCondition_1,
		usernameCondition_2,
		usernameCondition_3,
		passwordCondition_1,
		passwordCondition_2,
	} from "./inputValidator";
	const status: Ref<AuthStatus> = ref("normal");
	const loginStatus: LoginStatus = reactive({
		...defaultLoginStatus,
	});
	watch(status, () => {
		if (status.value === "fail") {
			setTimeout(() => {
				status.value = "normal";
			}, 3000);
		}
	});
	function validateFormData(): boolean {
		const uCondition_1 = usernameCondition_1(loginStatus.username.value);
		const uCondition_2 = usernameCondition_2(loginStatus.username.value);
		const uCondition_3 = usernameCondition_3(loginStatus.username.value);
		loginStatus.username.conditions.condition_1 = uCondition_1;
		loginStatus.username.conditions.condition_2 = uCondition_2;
		loginStatus.username.conditions.condition_3 = uCondition_3;
		const usernameConditions = uCondition_1 && uCondition_2 && uCondition_3;
		if (!usernameConditions) {
			loginStatus.username.status = "error";
		}
		const pCondition_1 = passwordCondition_1(loginStatus.password.value);
		const pCondition_2 = passwordCondition_2(loginStatus.password.value);
		loginStatus.password.conditions.condition_1 = pCondition_1;
		loginStatus.password.conditions.condition_2 = pCondition_2;
		const passwordConditions = pCondition_1 && pCondition_2;
		if (!passwordConditions) {
			loginStatus.password.status = "error";
		}
		if (usernameConditions && passwordConditions) {
			return true;
		} else {
			return false;
		}
	}
	async function submitForm(): Promise<void> {
		status.value = "loading";
		loginStatus.username.conditions.condition_4 = true;
		loginStatus.password.conditions.condition_4 = true;
		if (!validateFormData()) {
			status.value = "fail";
			return;
		}
		const loginFormData = new FormData();
		loginFormData.append("grantType", "password");
		loginFormData.append("username", loginStatus.username.value);
		loginFormData.append("password", loginStatus.password.value);
		try {
			const loginResponseData = await login(loginFormData);
			setAccessToken(loginResponseData);
			loginStatus.username.status = "success";
			loginStatus.password.status = "success";
			status.value = "success";
		} catch (error) {
			status.value = "fail";
			console.error(error);
			const errorResponse = error as APIError;
			switch (errorResponse.target) {
				case "username":
					loginStatus.username.status = "error";
					loginStatus.password.status = "normal";
					loginStatus.username.conditions.condition_4 = false;
					break;
				case "password":
					loginStatus.password.status = "error";
					loginStatus.username.status = "normal";
					loginStatus.password.conditions.condition_3 = false;
					break;
			}
		}
	}
</script>
<template>
	<form @submit.prevent="submitForm" class="parent-E17CUIEjke">
		<div class="parent-4JdRLIEoke">
			<div class="child-dyKyWLNiJl">
				<FormInput
					type="text"
					name="username"
					:auto-focus="true"
					placeholder="Enter your username"
					:status="loginStatus.username.status"
					v-model="loginStatus.username.value"
				/>
				<div class="child-VyRJw8Vjyg">
					<div
						class="child-4kGsZ_Eoye"
						v-if="loginStatus.username.status == 'error'"
					>
						<AuthFormErrorMessage
							class="child-VJLyS5Bj1e"
							:status="
								!loginStatus.username.conditions.condition_1
							"
						>
							Contains only lowercase letters, underscores, and
							numbers.
						</AuthFormErrorMessage>
						<AuthFormErrorMessage
							class="child-VJLyS5Bj1e"
							:status="
								!loginStatus.username.conditions.condition_2
							"
						>
							Must start with a lowercase letter.
						</AuthFormErrorMessage>
						<AuthFormErrorMessage
							class="child-VJLyS5Bj1e"
							:status="
								!loginStatus.username.conditions.condition_3
							"
						>
							The length is greater than or equal to 3 and less
							than or equal to 100.
						</AuthFormErrorMessage>
						<AuthFormErrorMessage
							class="child-VJLyS5Bj1e"
							:status="
								!loginStatus.username.conditions.condition_4
							"
						>
							This username does not exist.
						</AuthFormErrorMessage>
					</div>
				</div>
			</div>
			<div class="child-dyKyWLNiJl">
				<FormInput
					type="password"
					name="password"
					placeholder="Enter your password"
					:status="loginStatus.password.status"
					v-model="loginStatus.password.value"
				/>
				<div class="child-VyRJw8Vjyg">
					<div
						class="child-4kGsZ_Eoye"
						v-if="loginStatus.password.status == 'error'"
					>
						<AuthFormErrorMessage
							class="child-VJLyS5Bj1e"
							:status="
								!loginStatus.password.conditions.condition_1
							"
						>
							The length is greater than or equal to 6.
						</AuthFormErrorMessage>
						<AuthFormErrorMessage
							class="child-VJLyS5Bj1e"
							:status="
								!loginStatus.password.conditions.condition_2
							"
						>
							Must contain letters and numbers.
						</AuthFormErrorMessage>
						<AuthFormErrorMessage
							class="child-VJLyS5Bj1e"
							:status="
								!loginStatus.password.conditions.condition_3
							"
						>
							Wrong password.
						</AuthFormErrorMessage>
					</div>
				</div>
			</div>
		</div>
		<div class="parent-E1QvAI4o1g">
			<SubmitButton btn-type="login" :btn-status="status" />
		</div>
	</form>
</template>
<style src="./style.css"></style>
./localApi ../../../../.trash/defaults ../../../../.trash/auth/localApi
../../../../.trash/auth/remoteApi ../../../../.trash/auth/typing
