<script setup lang="ts">
	import { ref, watch, type Ref, reactive } from "vue";
	import RegisterForm from "@/components/auth/RegisterForm.vue";
	import LoginForm from "@/components/auth/LoginForm.vue";
	import Tags from "@/components/articleEditor/settings/Tags.vue";
	import SettingItem from "@/components/blog/SettingItem.vue";
	import { localArticle } from "@/components/articleEditor/localApi";
	import moment from "moment-timezone";
	import Datetime from "@/components/articleEditor/settings/Datetime.vue";
	import { dateFormatter } from "@/components/articleEditor/utlis";
	import Permalink from "@/components/articleEditor/settings/Permalink.vue";
	import { limString } from "@/api";

</script>

<template>

</template>

<style scoped></style>
