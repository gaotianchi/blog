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
	<div class="tile">
		<div class="overlay z-3" v-if="true">
			<div class="m-loader mr-4">
				<svg class="m-circular" viewBox="25 25 50 50">
					<circle
						class="path"
						cx="50"
						cy="50"
						r="20"
						fill="none"
						stroke-width="4"
						stroke-miterlimit="10"
					></circle>
				</svg>
			</div>
			<h3 class="l-text">正在加载 ...</h3>
		</div>
		<div class="tile-title">账户信息</div>
		<div class="tile-body row">
			<ul class="list-group list-group-flush">
				<li class="list-group-item">An item</li>
				<li class="list-group-item">A second item</li>
				<li class="list-group-item">A third item</li>
				<li class="list-group-item">A fourth item</li>
				<li class="list-group-item">And a fifth one</li>
			</ul>
		</div>
	</div>
</template>
<script lang="ts" setup>
	import MainPageHeaderComponent from '@/component/MainPageHeaderComponent.vue';
	import { onMounted } from 'vue';
	import ProfileSettingComponent from '@/component/setting.component/ProfileSettingComponent.vue';
</script>
