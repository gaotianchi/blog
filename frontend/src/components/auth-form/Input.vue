<script setup lang="ts">
const props = defineProps<{
    inputType?: "text" | "password";
    inputName: string;
    placeholder?: string;
    inputStatus: "normal" | "warning" | "error" | "success";
}>();
const model = defineModel();
</script>

<template>
<input 
:type="inputType ? inputType : 'text'" 
:name="inputName"
:placeholder="placeholder ? placeholder : `Enter ${inputName}`" 
:class="[inputStatus]"
v-model="model">
</template>

<style scoped>
input,
input.normal {
    width: 260px;
    height: 40px;

    padding: 0 8px;

    font-size: 1rem;

    border: none;
    outline: #8f8f8f solid 1px;
}


input.warning {
    outline: var(--warning-color) solid 2px;
}

input.error {
    outline: var(--error-color) solid 2px;
}

input.success {
    outline: var(--success-color) solid 3px;
}


input:focus,
input:focus-visible {
    outline: #8f8f8f solid 3px;
}
</style>