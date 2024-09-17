<template>
	<div class="tile position-relative">
		<div class="overlay z-3" v-if="state.loading">
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
					src="/public/default/avatar.svg"
					class="img-thumbnail"
					alt="avatar"
					width="200"
				/>
				<div class="position-absolute top-0 end-0" style="display: none">
					<AvatarEditorComponent buttonStyle="btn-link" />
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
									:readonly="state.field.penName.readonly"
									:class="[
										{
											'form-control-plaintext': state.field.penName.readonly,
											'form-control': !state.field.penName.readonly,
										},
									]"
									id="penname"
									name="penname"
									v-model="value.field.penName"
								/>
							</div>
							<div class="col-sm-1 text-end">
								<button
									@click="
										() => {
											if (state.field.penName.readonly) {
												state.field.penName.readonly = false;
											} else {
												value.field.penName = userResponse ?  userResponse.profile : "";
											}
										}
									"
									type="button"
									class="btn btn-link p-1 col-sm-auto"
								>
									<i
										class="bi"
										:class="{
											'bi-x-lg': !state.field.penName.readonly,
											'bi-pencil-square': state.field.penName.readonly,
										}"
									></i>
								</button>
							</div>
						</div>
					</li>
					<li class="list-group-item">
						<div class="row">
							<label for="timezone" class="col-sm-2 col-form-label">时区</label>
							<div class="col-sm-9">
								<select
									class="form-select"
									id="timezone"
									name="timezone"
									:disabled="state.field.timezone.disabled"
								>
									<option
										v-for="t in allWorldTimeZone"
										:value="t"
										:selected="t === value.field.timezone"
									>
										{{ t }}
									</option>
								</select>
							</div>
							<div class="col-sm-1 text-end">
								<button
									@click="() => {}"
									type="button"
									class="btn btn-link p-1 col-sm-auto"
								>
									<i
										class="bi"
										:class="{
											'bi-x-lg': !state.field.timezone.disabled,
											'bi-pencil-square': state.field.timezone.disabled,
										}"
									></i>
								</button>
							</div>
						</div>
					</li>
					<li class="list-group-item">
						<div class="row">
							<label for="userprofile" class="col-sm-2 col-form-label">简介</label>
							<div class="col-sm-9">
								<textarea
									class="form-control"
									name="userprofile"
									id="userprofile"
									:disabled="state.field.profile.disabled"
									rows="3"
									v-model="value.field.profile"
								></textarea>
							</div>
							<div class="col-sm-1 text-end">
								<button type="button" class="btn btn-link p-1 col-sm-auto">
									<i class="bi bi-pencil-square"></i>
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
			<div class="col-auto"></div>
		</div>
		<div
			v-if="
				state.field.penName.changed ||
				state.field.timezone.changed ||
				state.field.profile.changed
			"
			class="tile-footer text-end"
		>
			<button type="button" class="btn btn-secondary m-1">还原</button>
			<button type="button" class="btn btn-primary m-1">保存更改</button>
		</div>
	</div>
</template>
<script setup lang="ts">
	import AvatarEditorComponent from '@/component/AvatarEditorComponent.vue';
	import { computed, onMounted, reactive, ref } from 'vue';
	import { makeRequest } from '@/service/request.service';
	import type { APIResponse, UserResponse } from '@/type/response.type';
	import { allWorldTimeZone } from '@/const/timezone.const';
	const state = reactive({
		loading: true,
		field: {
			penName: {
				readonly: true,
				changed: computed(() => {
					return value.field.penName == userResponse.value?.penName;
				}),
			},
			timezone: {
				disabled: true,
				changed: computed(() => {
					return value.field.timezone == userResponse.value?.timezone;
				}),
			},
			profile: {
				disabled: true,
				changed: computed(() => {
					return value.field.profile == userResponse.value?.profile;
				}),
			},
		},
	});
	const value = reactive({
		field: {
			penName: '',
			timezone: '',
			profile: '',
		},
	});

	const userResponse = ref<UserResponse | null>(null);
	onMounted(async () => {
		const response: APIResponse<UserResponse> = await makeRequest('/users/get-info');
		if (response.code === 0) {
			userResponse.value = response.data;
			if (userResponse.value) {
				state.loading = false;
				value.field.penName = userResponse.value.penName;
				value.field.timezone = userResponse.value.timezone;
				value.field.profile = userResponse.value.profile;
				console.log(value.field);
				console.log(userResponse.value);
			} else {
				console.error(response.message);
			}
		}
	});
</script>
