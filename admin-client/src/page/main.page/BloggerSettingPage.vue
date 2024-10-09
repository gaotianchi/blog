<template>
	<MainPageHeaderComponent
		title="博主设置"
		subTitle="在这里配置博主的头像、笔名以及其他有关博主的个人信息。"
		bsIconName="bi-gear"
		:breadcrumbItems="[{ name: '博主设置', routeName: 'BLOGGER_SETTING' }]"
	/>
	<!-- 编辑个人简介 -->
	<ProfileSettingComponent />
	<!-- 编辑账户信息，邮箱或者密码 -->
	<AccountSettingComponent />
</template>
<script lang="ts" setup>
	import MainPageHeaderComponent from '@/component/MainPageHeaderComponent.vue';
	import ProfileSettingComponent from '@/component/setting.component/ProfileSettingComponent.vue';
	import AccountSettingComponent from '@/component/setting.component/AccountSettingComponent.vue';
</script>
