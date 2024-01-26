<script setup lang="ts">
import icons from '@/components/icons';
import { computed, ref } from 'vue';

const props = defineProps<{
    btnType: "login" | "register";
    btnStatus: "normal" | "loading" | "fail" | "success";
}>();

const btnItems = {
    login: {
        normal: {
            text: "Login",
            icon: icons.login,
        },
        fail: {
            text: "Fail",
            icon: icons.fail,
        },
        loading: {
            text: "Logining",
            icon: icons.loading,
        },
        success: {
            text: "Success",
            icon: icons.success,
        }
    },
    register: {
        normal: {
            text: "Register",
            icon: icons.register,
        },
        fail: {
            text: "Fail",
            icon: icons.fail,
        },
        loading: {
            text: "Registering",
            icon: icons.loading,
        },
        success: {
            text: "Success",
            icon: icons.success,
        }
    }
}

const btnContent = computed(() => {
    return btnItems[props.btnType][props.btnStatus];
})

</script>


<template>
<button 
type="submit"
:class="[btnType, btnStatus]">
<div class="btn-content-container">
<span class="bc-text">{{ btnContent.text }}</span>
<component class="bc-icon icon medium" :is="btnContent.icon" />
</div>
</button>
</template>

<style scoped>
button {
    width: 260px;
    height: 54px;

    display: flex;
    justify-content: center;
    align-items: center;

    border: none;
    cursor: pointer;
    border-radius: 50px;
}

.login.normal {
    background-color: var(--second-color);
}

.login.loading {
    background-color: var(--second-color);
    opacity: 0.7;
}

.register.normal {
    background-color: var(--first-color);
}

.register.loading {
    background-color: var(--first-color);
    opacity: 0.7;

    cursor: not-allowed;
}

.fail {
    background-color: var(--error-color);
    
    cursor: not-allowed;
}

.success {
    background-color: var(--success-color);
    
    cursor: not-allowed;
}

.btn-content-container {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.bc-text {
    margin-right: 10px;

    font-size: 1rem;
    font-weight: 400;
}

</style>