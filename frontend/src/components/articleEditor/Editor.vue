<script setup lang="ts">
	import "md-editor-v3/lib/style.css";
	import { MdEditor } from "md-editor-v3";
	import { reactive, ref, watch } from "vue";
	import type { Article } from "@/typing";
	import { isShallowEqual, serializeArticle } from "@/utlis";
	import { propConfirm, propMessage, localArticle } from "@/api/local";
	import { remoteArticle } from "@/api/remote";
	import icons from "@/components/icons";
	import Message from "../Message.vue";
	import Confirm from "../Confirm.vue";
	import SettingBar from "./SettingBar.vue";
	const elementsStatus = reactive({
		settingBtn: false,
		downBtn: false,
		sync: true,
	});
	watch(localArticle, () => {
		if (isShallowEqual(localArticle, remoteArticle)) {
			elementsStatus.sync = true;
		} else {
			elementsStatus.sync = false;
		}
	});
	function decideToPublishArticle(): void {
		if (elementsStatus.sync) {
			propMessage("Changes saved.");
			return;
		}
		propConfirm({
			header: "Publish Article",
			body: `Are you sure you want to pulish article 《${localArticle.title}》`,
			yesMessage: "Publish",
			noMessage: "Cancel",
			callback: patchArticleItem,
		});
	}
	async function patchArticleItem(): Promise<void> {
		console.log("Update article");
	}
</script>
<template>
	<Message />
	<Confirm />
	<form>
		<div class="parent-NJU0QvKikx">
			<input
				type="text"
				name="article-title"
				id="article-title"
				placeholder="Article title 100 words or less."
				aria-label="article-ttile"
				v-model="localArticle.title"
			/>
			<div class="parent-Vk3CQPKoJg">
				<component
					v-if="elementsStatus.sync"
					:is="icons.sync"
					class="parent-41MyNwFoJg icon"
				/>
				<component
					v-else
					:is="icons.unsync"
					class="parent-EkdyNPKiyl icon"
				/>
			</div>
			<div class="parent-4ypeVvYsyl">
				<div class="parent-Ey5UgdKiyx">
					<button
						type="button"
						@click="
							() => {
								elementsStatus.settingBtn =
									!elementsStatus.settingBtn;
								elementsStatus.downBtn = false;
							}
						"
						class="parent-EJWZ4DKoJe"
					>
						<component
							:is="icons.setting"
							class="parent-Ny-WMwFjJx icon"
						/>
					</button>
					<div
						class="parent-V1RmedFs1x"
						v-if="elementsStatus.settingBtn"
					>
						<h3 class="parent-Nk360OYjke">Article Settings</h3>
						<SettingBar />
					</div>
				</div>
				<div class="parent-VJIb4Ptokl">
					<button
						type="button"
						class="parent-NyNzVwYjkx child-Ek1DzDYjke"
					>
						<component
							:is="icons.view"
							class="parent-4JC44wYoJg icon"
						/>
						<span class="parent-4J6jDDFjyl">Preivew</span>
					</button>
					<div class="parent-NyxhQYFsyg">
						<button
							type="button"
							class="parent-4yuzEwtsJx child-Ek1DzDYjke"
							@click="
								() => {
									elementsStatus.downBtn =
										!elementsStatus.downBtn;
									elementsStatus.settingBtn = false;
								}
							"
						>
							<component
								:is="icons.down"
								class="parent-E1mV4vKj1g icon"
								:class="{ active: elementsStatus.downBtn }"
							/>
						</button>
						<div
							class="parent-VkiMVtYsJe"
							v-if="elementsStatus.downBtn"
						>
							<button
								type="button"
								class="child-E1C_VtYskl"
								v-if="remoteArticle.isPublished"
							>
								<component
									:is="icons.draft"
									class="icon child-E14lIKto1x black"
								/>
								<span class="child-EkIWUFYsJg"
									>Convert to draft</span
								>
							</button>
							<button
								type="button"
								class="child-E1C_VtYskl"
								v-if="remoteArticle.isPublished"
							>
								<component
									:is="icons.update"
									class="icon child-E14lIKto1x black"
								/>
								<span class="child-EkIWUFYsJg">Update</span>
							</button>
							<button
								type="button"
								class="child-E1C_VtYskl"
								v-if="!remoteArticle.isPublished"
							>
								<component
									:is="icons.publish"
									class="icon child-E14lIKto1x black"
								/>
								<span class="child-EkIWUFYsJg"
									>Publish article</span
								>
							</button>
							<div class="child-E1C_VtYskl">
								<component
									:is="icons.view"
									class="icon child-E14lIKto1x black"
								/>
								<span class="child-EkIWUFYsJg">Preivew</span>
							</div>
						</div>
					</div>
				</div>
				<button
					type="button"
					class="parent-VJ2zVvYsyg"
					@click="decideToPublishArticle"
				>
					<component
						v-if="!remoteArticle.isPublished"
						:is="icons.publish"
						class="parent-41VU4DYoke icon white"
					/>
					<component
						v-else
						:is="icons.update"
						class="parent-E1rPWqYsye icon white"
					/>
					<span
						class="parent-4ygQ4PKikg"
						v-if="!remoteArticle.isPublished"
						>Publish</span
					>
					<span class="parent-4ygQ4PKikg" v-else>Update</span>
				</button>
			</div>
		</div>
		<MdEditor v-model="localArticle.body" class="parent-4k6nVctskl" />
	</form>
</template>
<style scoped>
	@keyframes rotateAnimation {
		from {
			transform: rotate(0deg);
		}
		to {
			transform: rotate(-360deg);
		}
	}
	button {
		cursor: pointer;
		height: 45px;
		border: none;
	}
	#article-title {
		width: 100%;
		height: 45px;
		line-height: 20px;
		font-size: 20px;
		font-weight: bold;
		padding: 24px 5px 0 5px;
		border: none;
		border-bottom: var(--second-color) solid 1px;
		flex-grow: 1;
	}
	.parent-NJU0QvKikx {
		display: flex;
		justify-content: space-between;
		align-items: center;
		margin-bottom: 5px;
	}
	.parent-4ypeVvYsyl {
		display: flex;
		justify-content: flex-end;
		align-items: center;
	}
	.parent-EJWZ4DKoJe {
		width: 40px;
		border-radius: 5px;
		margin-left: 10px;
		display: flex;
		justify-content: center;
		align-items: center;
	}
	.parent-VJIb4Ptokl {
		width: fit-content;
		height: fit-content;
		display: flex;
		margin-left: 10px;
	}
	.parent-NyNzVwYjkx {
		display: flex;
		align-items: center;
		justify-content: center;
		border-radius: 5px 0 0 5px;
		width: 100px;
		border-right: #80808040 solid 1px;
	}
	.parent-4J6jDDFjyl {
		font-size: larger;
		margin-left: 5px;
	}
	.parent-VJ2zVvYsyg {
		display: flex;
		align-items: center;
		justify-content: center;
		width: 100px;
		background-color: var(--second-color);
		border-radius: 5px;
		margin: 0 10px;
	}
	.parent-4yuzEwtsJx {
		border-radius: 0 5px 5px 0;
	}
	.parent-E1mV4vKj1g {
		padding: 5px;
		transform: rotate(0deg);
		transition: transform 0.3s ease;
	}
	.parent-E1mV4vKj1g.active {
		transform: rotate(180deg);
	}
	.parent-4ygQ4PKikg {
		color: white;
		font-weight: bold;
		font-size: larger;
		margin-left: 5px;
	}
	.parent-Ey5UgdKiyx {
		position: relative;
		width: 45px;
		height: 45px;
	}
	.parent-V1RmedFs1x {
		position: absolute;
		z-index: 999;
		width: 320px;
		height: calc(100vh - 120px);
		padding: 10px;
		top: 50px;
		right: -148px;
		box-shadow: 0px 1px 3px 0px rgba(0, 0, 0, 0.25);
		background: rgb(255, 255, 255);
		overflow-y: auto;
		border-radius: 5px;
	}
	.parent-Nk360OYjke {
		margin-bottom: 10px;
		font-weight: 500;
		padding-left: 6px;
		color: grey;
	}
	.parent-Ny-WMwFjJx:hover {
		animation: rotateAnimation 1s linear;
	}
	.parent-NyxhQYFsyg {
		width: 36px;
		height: 45px;
		position: relative;
	}
	.parent-VkiMVtYsJe {
		position: absolute;
		width: 245px;
		height: fit-content;
		display: flex;
		flex-direction: column;
		right: -110px;
		top: 50px;
		box-shadow: 0px 1px 3px 0px rgba(0, 0, 0, 0.25);
		background: rgb(255, 255, 255);
		padding: 5px 0;
		z-index: 999;
	}
	.child-E1C_VtYskl {
		display: flex;
		align-items: center;
		height: 40px;
		cursor: pointer;
		padding: 5px 0 5px 25px;
		background-color: transparent;
	}
	.child-E1C_VtYskl:hover {
		background-color: #f0f0f0;
	}
	.child-EkIWUFYsJg {
		margin-left: 10px;
		font-size: 17px;
		line-height: 17px;
	}
	.parent-4k6nVctskl {
		min-height: calc(100vh -110px);
	}
</style>
