<script setup lang="ts">
	import { computed, reactive, ref, type Ref } from "vue";
	import FormInput from "./AuthFormInput.vue";
	import SubmitButton from "./AuthSubmitButton.vue";
	import AuthFormErrorMessage from "./AuthFormErrorMessage.vue";
	import type { AuthStatus, AuthFormData, RegisterStatus } from "./typing";
	import defaults from "./defaults";
	import { setRegisterStatus, getRegisterStatus } from "./localApi";
	const status: Ref<AuthStatus> = ref("normal");
	const registerStatus: RegisterStatus = reactive({ ...getRegisterStatus() });
	// const username: InputElement = reactive({ ...defaults.inputElement });
	// const password: InputElement = reactive({ ...defaults.inputElement });
	// const passwordConfirmation: InputElement = reactive({
	// 	...defaults.inputElement,
	// });
	const usernameConditon_1 = computed<boolean>(() => {
		const reg = new RegExp("^[a-z0-9_]+$");
		return reg.test(registerStatus.username.value);
	});
	const usernameConditon_2 = computed<boolean>(() => {
		const reg = new RegExp("^[a-z]");
		return reg.test(registerStatus.username.value);
	});
	const usernameConditon_3 = computed<boolean>(() => {
		return (
			registerStatus.username.value.length > 2 &&
			registerStatus.username.value.length < 100
		);
	});
	const passwordCondition_1 = computed<boolean>(() => {
		return registerStatus.password.value.length > 5;
	});
	const passwordCondition_2 = computed<boolean>(() => {
		const reg = new RegExp("^(?=.*[a-zA-Z])(?=.*\\d).$");
		return reg.test(registerStatus.password.value);
	});
	function submitForm(): void {
		
		registerStatus.username.conditions.usernameConditon_1 =
			usernameConditon_1.value;
		registerStatus.username.conditions.usernameConditon_2 =
			usernameConditon_2.value;
		registerStatus.username.conditions.usernameConditon_3 =
			usernameConditon_3.value;
		if (
			usernameConditon_1.value &&
			usernameConditon_2.value &&
			usernameConditon_3.value
		) {
			registerStatus.username.status = "success";
		} else {
			registerStatus.username.status = "error";
		}
		registerStatus.password.conditions.passwordCondition_1 =
			passwordCondition_1.value;
		registerStatus.password.conditions.passwordCondition_2 =
			passwordCondition_2.value;

		if (passwordCondition_1.value && passwordCondition_2.value) {
			registerStatus.password.status = "success";
		} else {
			registerStatus.password.status = "error";
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
						v-if="registerStatus.username.status != 'normal'"
					>
						<AuthFormErrorMessage
							class="child-VJLyS5Bj1e"
							:status="
								!registerStatus.username.conditions
									.usernameCondition_1
							"
						>
							Contains only lowercase letters, underscores, and
							numbers.
						</AuthFormErrorMessage>
						<AuthFormErrorMessage
							class="child-VJLyS5Bj1e"
							:status="
								!registerStatus.username.conditions
									.usernameCondition_2
							"
						>
							Must start with a lowercase letter.
						</AuthFormErrorMessage>
						<AuthFormErrorMessage
							class="child-VJLyS5Bj1e"
							:status="
								!registerStatus.username.conditions
									.usernameCondition_3
							"
						>
							The length is greater than or equal to 3 and less
							than or equal to 100.
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
						v-if="registerStatus.password.status != 'normal'"
					>
						<AuthFormErrorMessage
							class="child-VJLyS5Bj1e"
							:status="
								!registerStatus.password.conditions
									.passwordCondition_1
							"
						>
							The length is greater than or equal to 6.
						</AuthFormErrorMessage>
						<AuthFormErrorMessage
							class="child-VJLyS5Bj1e"
							:status="
								!registerStatus.password.conditions
									.passwordCondition_2
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
					<div class="child-4kGsZ_Eoye">
						<!-- <AuthFormMessage :status=""></AuthFormMessage> -->
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
