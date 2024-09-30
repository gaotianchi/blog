<template>
	<div class="tile position-relative">
		<TileLoadingComponent :loading="loading" />
		<div class="tile-title">个人简介</div>
		<div class="tile-body row">
			<div class="col-auto">
				<AvatarEditorComponent
					:avatar="avatarInfo"
					@save-change="avatar => handleSaveAvatarChange(avatar)"
				/>
			</div>
			<div class="col">
				<ul class="list-group list-group-flush">
					<li class="list-group-item">
						<InputComponent
							label-name="笔名"
							input-type="text"
							:readonly="penName.readonly"
							v-model="penName.currentValue"
							@edit="penName.readonly = false"
							@reset="handleReset('penName')"
						/>
					</li>
					<li class="list-group-item">
						<InputComponent
							label-name="时区"
							input-type="select"
							:readonly="timezone.readonly"
							:select-options="allWorldTimeZone"
							v-model="timezone.currentValue"
							@edit="timezone.readonly = false"
							@reset="handleReset('timezone')"
						/>
					</li>
					<li class="list-group-item">
						<InputComponent
							label-name="简介"
							input-type="textarea"
							:readonly="profile.readonly"
							v-model="profile.currentValue"
							@edit="profile.readonly = false"
							@reset="handleReset('profile')"
						/>
					</li>
				</ul>
			</div>
		</div>
		<div
			v-if="penName.changed || timezone.changed || profile.changed"
			class="tile-footer text-end"
		>
			<button @click="handleSaveChange" type="button" class="btn btn-primary m-1">
				保存更改
			</button>
		</div>
	</div>
</template>
<script setup lang="ts">
	import AvatarEditorComponent from '@/component/setting.component/AvatarEditorComponent.vue';
	import { computed, onMounted, reactive, ref, watch } from 'vue';
	import { makeRequest } from '@/service/request.service';
	import { allWorldTimeZone } from '@/const/timezone.const';
	import showMessage from '@/service/alert.service';
	import { AlertType } from '@/enum';
	import { RESOURCE_BASE_URL } from '@/config/global.config';
	import TileLoadingComponent from '../TileLoadingComponent.vue';
	import InputComponent from '../InputComponent.vue';
	import type { APIResponse, AvatarInfo, UserInfo } from '@/type/response.type';

	const loading = ref(false);

	const penName = reactive({
		currentValue: '',
		changed: false,
		readonly: true,
	});

	const timezone = reactive({
		currentValue: '',
		changed: false,
		readonly: true,
	});

	const profile = reactive({
		currentValue: '',
		changed: false,
		readonly: true,
	});

	const userInfo = ref<UserInfo | null>(null);
	const avatarInfo = ref<AvatarInfo | null>(null);

	const handleSaveAvatarChange = (avatar: AvatarInfo) => {
		avatarInfo.value = avatar;
	};

	const handleSaveChange = async () => {
		const response: APIResponse<void> = await makeRequest(RESOURCE_BASE_URL + '/users/info', {
			method: 'PATCH',
			body: JSON.stringify({
				penName: penName.currentValue,
				timezone: timezone.currentValue,
				profile: profile.currentValue,
			}),
		});
		if (response.code === 0) {
			showMessage('成功更新个人简介', AlertType.SUCCESS);
			if (userInfo.value) {
				userInfo.value.penName = penName.currentValue;
				userInfo.value.timeZone = timezone.currentValue;
				userInfo.value.profile = profile.currentValue;
			}
			resetStatus();
		} else {
			showMessage('更新失败', AlertType.ERROR);
		}
	};

	const resetStatus = () => {
		penName.changed = false;
		timezone.changed = false;
		profile.changed = false;

		penName.readonly = true;
		timezone.readonly = true;
		profile.readonly = true;
	};

	onMounted(async () => {
		const response: APIResponse<UserInfo> = await makeRequest(
			RESOURCE_BASE_URL + '/users/info'
		);
		penName.currentValue = response.data.penName;
		timezone.currentValue = response.data.timeZone;
		profile.currentValue = response.data.profile;
		userInfo.value = response.data;

		const avatarInfoLocation = response.data.avatarInfoLocation;
		if (avatarInfoLocation) {
			const avatarInfoResponse: APIResponse<AvatarInfo> = await makeRequest(
				response.data.avatarInfoLocation
			);
			avatarInfo.value = avatarInfoResponse.data;
		}
	});

	watch(
		() => penName.currentValue,
		newValue => {
			penName.changed = newValue != userInfo.value?.penName;
		}
	);

	watch(
		() => timezone.currentValue,
		newValue => {
			timezone.changed = newValue != userInfo.value?.timeZone;
		}
	);

	watch(
		() => profile.currentValue,
		newValue => {
			profile.changed = newValue != userInfo.value?.profile;
		}
	);

	const handleReset = (name: 'penName' | 'timezone' | 'profile') => {
		switch (name) {
			case 'penName':
				penName.currentValue = userInfo.value?.penName || '';
				break;
			case 'profile':
				profile.currentValue = userInfo.value?.profile || '';
			case 'timezone':
				timezone.currentValue = userInfo.value?.timeZone || '';
			default:
				break;
		}
	};
</script>
