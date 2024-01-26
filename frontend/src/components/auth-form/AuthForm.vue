<script setup lang="ts">
import Input from './Input.vue';
import SubmitBtn from './SubmitBtn.vue';
import { ref, watchEffect, type Ref } from 'vue';


type FormStatus = "normal" | "fail" | "success" | "loading";
type FormAction = "register" | "login";

type InputItems = {
    value: string;
    status: "normal" | "warning" | "error" | "success";
    message: string;
}

const status: Ref<FormStatus> = ref("normal");
const action: Ref<FormAction> = ref("register");

const username: Ref<InputItems> = ref({
    value: "",
    status: "normal",
    message: "",
})

const password: Ref<InputItems> = ref({
    value: "",
    status: "normal",
    message: "",
})

const password2: Ref<InputItems> = ref({
    value: "",
    status: "normal",
    message: "",
})

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

function emptyNotification(item: InputItems, message: string | null = null): void {
    if (message != null) {
        item.message = message;
        item.status = "warning";
    } else {
        item.message = "This area cannot be empty.";
        item.status = "warning";
    }
}

function emptyValidator(items: InputItems[]): boolean {
    const errors = [];
    items.forEach((item) => {
        if (item.value.length === 0) {
            errors.push(item);
            emptyNotification(item)
        }
    });

    if (errors.length > 0) {
        return true;
    } else {
        return false;
    }
}

function patternNotification(item: InputItems, message: string | null = null): void {
    if (message) {
        item.message = message;
        item.status = "warning";
    } else {
        item.message = "Invalid pattern."
        item.status = "warning";
    }
}


function patternValidator(items: {it: InputItems, pattern: RegExp, message: string}[]): boolean {
    const errors = [];
    items.forEach((item) => {
        if (!item.pattern.test(item.it.value)) {
            errors.push(item);
            patternNotification(item.it, item.message);
        }
    })

    if (errors.length > 0) {
        return true;
    } else {
        return false;
    }
}

const inputItemPatterns = [
    {
        it: username.value,
        pattern: RegExp("^[a-z][a-z0-9_]{2,49}$"),
        message: "Ensures valid usernames: 3-50 characters, starts with a lowercase letter, allows only lowercase letters, numbers, and underscores."
    },
    {
        it: password.value,
        pattern: RegExp("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{7,}$"),
        message: "Enforces secure passwords: at least 7 characters with a mix of uppercase and lowercase letters, numbers, and special symbols."
    }
]

function submitForm(): void {
    status.value = "loading";
    username.value.message = "";
    password.value.message = "";
    password2.value.message = "";

    const requiredItems = [username.value, password.value, password2.value]
    const emptyError = emptyValidator(requiredItems);
    if (emptyError) {
        status.value = "fail";
        return;
    }

    const patternError = patternValidator(inputItemPatterns);
    if (patternError) {
        status.value = "fail";
        return;
    }
}


</script>

<template>
<form @submit.prevent="submitForm">
    <div class="inputs-messages-submitbtn">
        <div class="im-container">
            <div class="imc-items">
                <Input 
                input-name="username"
                v-model="username.value"
                :input-status="username.status"/>
                <span class="imc-message">
                    {{ username.message }}
                </span>
            </div>
            <div class="imc-items">
                <Input 
                input-type="password"
                input-name="password"
                v-model="password.value"
                :input-status="password.status"/>
                <span class="imc-message">
                    {{ password.message }}
                </span>
            </div>
            <div class="imc-items">
                <Input 
                input-type="password"
                input-name="password2"
                placeholder="Repeat password"
                v-model="password2.value"
                :input-status="password2.status"/>
                <span class="imc-message">
                    {{ password2.message }}
                </span>
            </div>
        </div>
        <SubmitBtn :btn-type="action" :btn-status="status" />
    </div>
</form>
</template>

<style scoped>
form {
    display: flex;
    justify-content: center;
    align-items: center;

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
</style>