<script setup lang="ts">
	import { computed, reactive, ref, type Ref } from "vue";
	import FormInput from "./AuthFormInput.vue";
	import SubmitButton from "./AuthSubmitButton.vue";
	import AuthFormMessage from "./AuthFormMessage.vue";
	import type { AuthStatus, InputStatus, AuthFormData } from "./typing";
	import { authFormDataValidate } from "./schemas";
	import { login } from "@/api/auth";
	import Ajv from "ajv";
	type InputElement = {
		status: InputStatus;
		value: string;
	};
	const ajv = new Ajv();
	const status: Ref<AuthStatus> = ref("normal");
	const username: InputElement = reactive({
		status: "normal",
		value: "",
	});
	const password: InputElement = reactive({
		status: "normal",
		value: "",
	});
	const passwordConfirmation: InputElement = reactive({
		status: "normal",
		value: "",
	});
	const usernameConditon_1 = computed<boolean>(() => {
		const reg = new RegExp("^[a-z0-9_]+$");
		return reg.test(username.value);
	});
	const usernameConditon_2 = computed<boolean>(() => {
		const reg = new RegExp("^[a-z]");
		return reg.test(username.value);
	});
	function submitForm(): void {
		const authFormData: AuthFormData = {
			username: username.value,
			password: password.value,
		};
		const validator = authFormDataValidate(authFormData);
        
	}
</script>
<template>
	<form @submit.prevent="" class="parent-E17CUIEjke">
		<div class="parent-4JdRLIEoke">
			<div class="child-dyKyWLNiJl">
				<FormInput
					type="text"
					name="username"
					:auto-focus="true"
					placeholder="Enter your username"
					:status="username.status"
					v-model="username.value"
				/>
				<div class="child-VyRJw8Vjyg">
					<div
						class="child-4kGsZ_Eoye"
						v-if="username.status != 'normal'"
					>
						<AuthFormMessage :status="usernameConditon_1">
							Contains only lowercase letters, underscores, and
							numbers;
						</AuthFormMessage>
						<AuthFormMessage :status="usernameConditon_2">
							Must start with a lowercase letter;
						</AuthFormMessage>
					</div>
				</div>
			</div>
			<div class="child-dyKyWLNiJl">
				<FormInput
					type="password"
					name="password"
					placeholder="Enter your password"
					:status="password.status"
					v-model="password.value"
				/>
				<div class="child-VyRJw8Vjyg">
					<div class="child-4kGsZ_Eoye">
						<!-- <AuthFormMessage :status=""></AuthFormMessage> -->
					</div>
				</div>
			</div>
			<div class="child-dyKyWLNiJl">
				<FormInput
					type="password"
					name="passwordConfirmation"
					placeholder="Confirm your password"
					:status="passwordConfirmation.status"
					v-model="passwordConfirmation.value"
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
