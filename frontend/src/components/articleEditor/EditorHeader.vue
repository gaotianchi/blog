<script setup lang="ts">
	import { inject, reactive, watch } from "vue";
	import { isShallowEqual, serializeArticle } from "@/utlis";
	import { propConfirm, propMessage, localArticle } from "@/api/local";
	import { remoteArticle, patchArticleItem } from "@/api/remote";
	import icons from "@/components/icons";
	import Message from "../Message.vue";
	import Confirm from "../Confirm.vue";
	import SettingBar from "./SettingBar.vue";
	const articleId = inject("articleId");
	const elementsStatus = reactive({
		settingBtn: false,
		downBtn: false,
		sync: true,
		update: false,
	});
	watch(
		localArticle,
		() => {
			if (isShallowEqual(localArticle, remoteArticle)) {
				elementsStatus.sync = true;
			} else {
				elementsStatus.sync = false;
			}
		},
		{
			immediate: true,
		}
	);
	function decideToPublishArticle(): void {
		propConfirm({
			header: "Publish Article",
			body: `Are you sure you want to pulish article 《${localArticle.title}》`,
			yesMessage: "Publish",
			noMessage: "Cancel",
			callback: publishArticleItem,
		});
	}
	async function updateArticleItem(): Promise<void> {
		propMessage("Updating article...");
		if (elementsStatus.sync) {
			propMessage("Changes saved.");
			return;
		}
		elementsStatus.update = true;
		try {
			const response = await patchArticleItem(
				articleId as string,
				serializeArticle(localArticle)
			);
			Object.assign(remoteArticle, response);
			Object.assign(localArticle, response);
			propMessage("Changes saved.");
			elementsStatus.update = false;
		} catch (error) {
			console.error(error);
		}
	}
	function publishArticleItem(): void {
		localArticle.isPublished = true;
		elementsStatus.sync = false;
		updateArticleItem();
	}
	function convertToDraft(): void {
		localArticle.isPublished = false;
		elementsStatus.sync = false;
		updateArticleItem();
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
								@click="convertToDraft"
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
								@click="updateArticleItem"
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
								@click="decideToPublishArticle"
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
					@click="
						localArticle.isPublished
							? updateArticleItem()
							: decideToPublishArticle()
					"
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
						:class="{ active: elementsStatus.update }"
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
	</form>
</template>
