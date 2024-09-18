<template>
	<div class="tile position-relative">
		<div class="overlay z-3" v-if="loading">
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
		<div class="tile-title">个人简介</div>
		<div class="tile-body row">
			<div class="col-auto position-relative">
				<img
					:src="
						currentAvatar?.urls.ORIGINAL
							? currentAvatar?.urls.ORIGINAL
							: '/default/avatar.svg'
					"
					class="img-thumbnail"
					alt="avatar"
					width="200"
				/>
				<div class="position-absolute top-0 end-0">
					<AvatarEditorComponent
						buttonStyle="btn-link"
						:init-url="currentAvatar?.urls.ORIGINAL"
						@save="handleSavedAvatar"
					/>
				</div>
			</div>
			<div class="col">
				<ul class="list-group list-group-flush">
					<li class="list-group-item">
						<div class="row">
							<label for="penname" class="col-sm-2 col-form-label">笔名</label>
							<div class="col-sm-9">
								<input
									type="text"
									:readonly="penNameReadonly"
									:class="[
										{
											'form-control-plaintext': penNameReadonly,
											'form-control': !penNameReadonly,
										},
									]"
									id="penname"
									name="penname"
									v-model="currentPenName"
								/>
							</div>
							<div class="col-sm-1 text-end">
								<EditorButtonComponent
									:readingState="penNameReadonly"
									@edit="
										() => {
											penNameReadonly = false;
										}
									"
									@reset="
										() => {
											penNameReadonly = true;
											currentPenName =
												userResponse?.penName || currentPenName;
										}
									"
								/>
							</div>
						</div>
					</li>
					<li class="list-group-item">
						<div class="row">
							<label for="timezone" class="col-sm-2 col-form-label">时区</label>
							<div class="col-sm-9">
								<select
									:class="[
										{
											'form-control-plaintext': timezoneDisabled,
											'form-select': !timezoneDisabled,
										},
									]"
									id="timezone"
									name="timezone"
									:disabled="timezoneDisabled"
									v-model="currentTimezone"
									:style="{ appearance: timezoneDisabled ? 'none' : 'initial' }"
								>
									<option
										v-for="t in allWorldTimeZone"
										:value="t"
										:selected="t === currentTimezone"
									>
										{{ t }}
									</option>
								</select>
							</div>
							<div class="col-sm-1 text-end">
								<EditorButtonComponent
									:readingState="timezoneDisabled"
									@edit="
										() => {
											timezoneDisabled = false;
										}
									"
									@reset="
										() => {
											timezoneDisabled = true;
											currentTimezone = userResponse?.timezone
												? userResponse.timezone
												: currentTimezone;
										}
									"
								/>
							</div>
						</div>
					</li>
					<li class="list-group-item">
						<div class="row">
							<label for="userprofile" class="col-sm-2 col-form-label">简介</label>
							<div class="col-sm-9">
								<textarea
									:class="[
										{
											'form-control-plaintext': profileDisabled,
											'form-control': !profileDisabled,
										},
									]"
									name="userprofile"
									id="userprofile"
									rows="3"
									v-model="currentProfile"
									:disabled="profileDisabled"
									:readonly="profileDisabled"
									:style="{ resize: profileDisabled ? 'none' : 'initial' }"
								></textarea>
							</div>
							<div class="col-sm-1 text-end">
								<EditorButtonComponent
									:readingState="profileDisabled"
									@edit="
										() => {
											profileDisabled = false;
										}
									"
									@reset="
										() => {
											profileDisabled = true;
											currentProfile = userResponse?.profile
												? userResponse.profile
												: currentProfile;
										}
									"
								/>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
		<div
			v-if="penNameChanged || timezoneChanged || profileChanged || avatarChanged"
			class="tile-footer text-end"
		>
			<button @click="saveChange" type="button" class="btn btn-primary m-1">保存更改</button>
		</div>
	</div>
</template>
<script setup lang="ts">
	import AvatarEditorComponent from '@/component/AvatarEditorComponent.vue';
	import { computed, onMounted, ref } from 'vue';
	import { makeRequest } from '@/service/request.service';
	import type { APIResponse, ImageResponse, UserResponse } from '@/type/response.type';
	import { allWorldTimeZone } from '@/const/timezone.const';
	import EditorButtonComponent from './EditorButtonComponent.vue';
	import showMessage from '@/service/alert.service';
	import { AlertType } from '@/enum';

	// 状态变量
	const loading = ref(true);
	const penNameChanged = computed(() => currentPenName.value != userResponse.value?.penName);
	const timezoneChanged = computed(() => currentTimezone.value != userResponse.value?.timezone);
	const profileChanged = computed(() => currentProfile.value != userResponse.value?.profile);
	const avatarChanged = computed(() => currentAvatar.value?.id != userResponse.value?.avatar?.id);
	const penNameReadonly = ref(true);
	const timezoneDisabled = ref(true);
	const profileDisabled = ref(true);

	// 当前输入框的值
	const currentPenName = ref('');
	const currentTimezone = ref('');
	const currentProfile = ref('');
	const currentAvatar = ref<ImageResponse | null>(null);

	// 初始化组件数据
	const userResponse = ref<UserResponse | null>(null);
	onMounted(async () => {
		const response: APIResponse<UserResponse> = await makeRequest(
			'/users/get-info',
			'resource'
		);
		if (response.code === 0) {
			userResponse.value = response.data;
			if (userResponse.value) {
				loading.value = false;
				currentAvatar.value = userResponse.value.avatar;
				currentPenName.value = userResponse.value.penName;
				currentProfile.value = userResponse.value.profile;
				currentTimezone.value = userResponse.value.timezone;
				console.log(userResponse.value);
			} else {
				console.error(response.message);
			}
		}
	});

	// 工具函数
	const handleSavedAvatar = (avatar: ImageResponse) => {
		currentAvatar.value = avatar;
	};

	const saveChange = async () => {
		penNameReadonly.value = true;
		timezoneDisabled.value = true;
		profileDisabled.value = true;
		const response: APIResponse<UserResponse> = await makeRequest(
			'/users/update-info',
			'resource',
			{
				method: 'PATCH',
				body: JSON.stringify({
					penName: currentPenName.value,
					avatarId: currentAvatar.value?.id,
					timezone: currentTimezone.value,
					profile: currentProfile.value,
				}),
			}
		);
		if (response.code === 0) {
			showMessage('个人简介更新成功。', AlertType.SUCCESS);
			userResponse.value = response.data;
		} else {
			showMessage('个人简介更新失败，请稍后重试！', AlertType.ERROR);
		}
	};
</script>
