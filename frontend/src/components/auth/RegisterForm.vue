<script setup lang="ts">
	import { useRouter } from "vue-router";
	import { computed, reactive, ref, watch, type Ref } from "vue";
	import type { AuthStatus, RegisterStatus } from "@/typing";
	import { defaultRegisterStatus } from "@/defaults";
	import { registerUser } from "@/api/remote";
	import type { APIError } from "@/api/errors";
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
	const router = useRouter();
	const status: Ref<AuthStatus> = ref("normal");
	const registerStatus: RegisterStatus = reactive({
		...defaultRegisterStatus,
	});
	const passwordConfirmationCondition_1 = computed<boolean>(() => {
		return (
			registerStatus.password.value ===
			registerStatus.passwordConfirmation.value
		);
	});
	const passwordConfirmationCondition_2 = computed<boolean>(() => {
		return registerStatus.passwordConfirmation.value.length > 0;
	});
	watch(status, () => {
		if (status.value === "fail") {
			setTimeout(() => {
				status.value = "normal";
			}, 3000);
		}
	});
	function validateFormData(): boolean {
		const uCondition_1 = usernameCondition_1(registerStatus.username.value);
		const uCondition_2 = usernameCondition_2(registerStatus.username.value);
		const uCondition_3 = usernameCondition_3(registerStatus.username.value);
		registerStatus.username.conditions.condition_1 = uCondition_1;
		registerStatus.username.conditions.condition_2 = uCondition_2;
		registerStatus.username.conditions.condition_3 = uCondition_3;
		const usernameConditions = uCondition_1 && uCondition_2 && uCondition_3;
		if (!usernameConditions) {
			registerStatus.username.status = "error";
		}
		const pCondition_1 = passwordCondition_1(registerStatus.password.value);
		const pCondition_2 = passwordCondition_2(registerStatus.password.value);
		registerStatus.password.conditions.condition_1 = pCondition_1;
		registerStatus.password.conditions.condition_2 = pCondition_2;
		const passwordConditions = pCondition_1 && pCondition_2;
		if (passwordConditions) {
			registerStatus.password.status = "success";
		} else {
			registerStatus.password.status = "error";
		}
		registerStatus.passwordConfirmation.conditions.condition_1 =
			passwordConfirmationCondition_1.value;
		registerStatus.passwordConfirmation.conditions.condition_2 =
			passwordConfirmationCondition_2.value;
		const passwordConfirmationConditions =
			passwordConfirmationCondition_1.value &&
			passwordConfirmationCondition_2.value;
		if (passwordConfirmationConditions) {
			registerStatus.passwordConfirmation.status = "success";
		} else {
			registerStatus.passwordConfirmation.status = "error";
		}
		if (
			usernameConditions &&
			passwordConditions &&
			passwordConfirmationConditions
		) {
			return true;
		} else {
			return false;
		}
	}
	async function submitForm(): Promise<void> {
		registerStatus.username.conditions.condition_4 = true;
		status.value = "loading";
		if (!validateFormData()) {
			status.value = "fail";
			return;
		}
		const registerFormData = new FormData();
		registerFormData.append("username", registerStatus.username.value);
		registerFormData.append("password", registerStatus.password.value);
		try {
			await registerUser(registerFormData);
			status.value = "success";
			registerStatus.username.status = "success";
			setTimeout(() => {
				router.push({ name: "Login" });
			}, 2000);
			return;
		} catch (error) {
			console.error(error);
			status.value = "fail";
			const apiError = error as APIError;
			if (apiError.target === "username") {
				registerStatus.username.status = "error";
				registerStatus.username.conditions.condition_4 = false;
			}
			return;
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
					:status="registerStatus.username.status"
					v-model="registerStatus.username.value"
				/>
				<div class="child-VyRJw8Vjyg">
					<div
						class="child-4kGsZ_Eoye"
						v-if="registerStatus.username.status == 'error'"
					>
						<AuthFormErrorMessage
							class="child-VJLyS5Bj1e"
							:status="
								!registerStatus.username.conditions.condition_1
							"
						>
							Contains only lowercase letters, underscores, and
							numbers.
						</AuthFormErrorMessage>
						<AuthFormErrorMessage
							class="child-VJLyS5Bj1e"
							:status="
								!registerStatus.username.conditions.condition_2
							"
						>
							Must start with a lowercase letter.
						</AuthFormErrorMessage>
						<AuthFormErrorMessage
							class="child-VJLyS5Bj1e"
							:status="
								!registerStatus.username.conditions.condition_3
							"
						>
							The length is greater than or equal to 3 and less
							than or equal to 100.
						</AuthFormErrorMessage>
						<AuthFormErrorMessage
							class="child-VJLyS5Bj1e"
							:status="
								!registerStatus.username.conditions.condition_4
							"
						>
							This username has been used.
						</AuthFormErrorMessage>
					</div>
				</div>
			</div>
			<div class="child-dyKyWLNiJl">
				<FormInput
					type="password"
					name="password"
					placeholder="Enter your password"
					:status="registerStatus.password.status"
					v-model="registerStatus.password.value"
				/>
				<div class="child-VyRJw8Vjyg">
					<div
						class="child-4kGsZ_Eoye"
						v-if="registerStatus.password.status == 'error'"
					>
						<AuthFormErrorMessage
							class="child-VJLyS5Bj1e"
							:status="
								!registerStatus.password.conditions.condition_1
							"
						>
							The length is greater than or equal to 6.
						</AuthFormErrorMessage>
						<AuthFormErrorMessage
							class="child-VJLyS5Bj1e"
							:status="
								!registerStatus.password.conditions.condition_2
							"
						>
							Must contain letters and numbers.
						</AuthFormErrorMessage>
					</div>
				</div>
			</div>
			<div class="child-dyKyWLNiJl">
				<FormInput
					type="password"
					name="passwordConfirmation"
					placeholder="Confirm your password"
					:status="registerStatus.passwordConfirmation.status"
					v-model="registerStatus.passwordConfirmation.value"
				/>
				<div class="child-VyRJw8Vjyg">
					<div
						class="child-4kGsZ_Eoye"
						v-if="
							registerStatus.passwordConfirmation.status ==
							'error'
						"
					>
						<AuthFormErrorMessage
							class="child-VJLyS5Bj1e"
							:status="
								!registerStatus.passwordConfirmation.conditions
									.condition_1
							"
						>
							Must be consistent with the previous password.
						</AuthFormErrorMessage>
						<AuthFormErrorMessage
							class="child-VJLyS5Bj1e"
							:status="
								!registerStatus.passwordConfirmation.conditions
									.condition_2
							"
						>
							This field cannot be empty.
						</AuthFormErrorMessage>
					</div>
				</div>
			</div>
		</div>
		<div class="parent-E1QvAI4o1g">
			<SubmitButton btn-type="register" :btn-status="status" />
		</div>
	</form>
</template>

