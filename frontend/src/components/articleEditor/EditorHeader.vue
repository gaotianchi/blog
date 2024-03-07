<script setup lang="ts">
	import { inject, reactive, watch } from "vue";
	import { isSameArticle, serializeArticle } from "@/utlis";
	import { propConfirm, propMessage } from "@/api/local";
	import { patchArticleItem } from "@/api/remote";
	import icons from "@/components/icons";
	import SettingBar from "./SettingBar.vue";
	import { editorLocalAndRemote, ArticleCards } from "@/store";
	const articleId = inject("articleId") as number;
	const elementsStatus = reactive({
		settingBtn: false,
		downBtn: false,
		sync: true,
		update: false,
	});
	watch(editorLocalAndRemote, () => {
		if (
			isSameArticle(
				editorLocalAndRemote[articleId].local,
				editorLocalAndRemote[articleId].remote
			)
		) {
			elementsStatus.sync = true;
		} else {
			elementsStatus.sync = false;
		}
	});
	function decideToPublishArticle(): void {
		propConfirm({
			header: "Publish Article",
			body: `Are you sure you want to pulish article 《${editorLocalAndRemote[articleId].local.title}》`,
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
				articleId,
				serializeArticle(editorLocalAndRemote[articleId].local)
			);
			Object.assign(editorLocalAndRemote[articleId].remote, response);
			Object.assign(editorLocalAndRemote[articleId].local, response);
			propMessage("Changes saved.");
			elementsStatus.update = false;
		} catch (error) {
			console.error(error);
		}
	}
	function publishArticleItem(): void {
		editorLocalAndRemote[articleId].local.isPublished = true;
		elementsStatus.sync = false;
		updateArticleItem().then(() => {
			const curr = ArticleCards.card(articleId);
			curr.isPublished = true;
			ArticleCards.update(curr);
		});
	}
	function convertToDraft(): void {
		editorLocalAndRemote[articleId].local.isPublished = false;
		elementsStatus.sync = false;
		updateArticleItem().then(() => {
			const curr = ArticleCards.card(articleId);
			curr.isPublished = false;
			ArticleCards.update(curr);
		});
	}
</script>
<template>
	<form>
		<div class="parent-NJU0QvKikx">
			<input
				type="text"
				name="article-title"
				id="article-title"
				placeholder="Article title 100 words or less."
				aria-label="article-title"
				v-model="editorLocalAndRemote[articleId].local.title"
			/>
			<div class="parent-Vk3CQPKoJg">
				<component
					v-if="elementsStatus.sync"
					:is="icons.sync"
					class="parent-41MyNwFoJg icon"
					@click="() => console.log(editorLocalAndRemote)"
				/>
				<component
					v-else
					:is="icons.unsync"
					class="parent-EkdyNPKiyl icon"
					@click="() => console.log(editorLocalAndRemote)"
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
								@click="convertToDraft"
							>
								<component
									:is="icons.draft"
									class="icon child-E14lIKto1x black"
								/>
								<span class="child-EkIWUFYsJg">
									{{
										editorLocalAndRemote[articleId].local
											.isPublished
											? "Convert to draft"
											: "Save as draft"
									}}
								</span>
							</button>
							<button
								type="button"
								class="child-E1C_VtYskl"
								v-if="
									editorLocalAndRemote[articleId].remote
										.isPublished
								"
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
								v-if="
									!editorLocalAndRemote[articleId].remote
										.isPublished
								"
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
						editorLocalAndRemote[articleId].local.isPublished
							? updateArticleItem()
							: decideToPublishArticle()
					"
				>
					<component
						v-if="
							!editorLocalAndRemote[articleId].remote.isPublished
						"
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
						v-if="
							!editorLocalAndRemote[articleId].remote.isPublished
						"
						>Publish</span
					>
					<span class="parent-4ygQ4PKikg" v-else>Update</span>
				</button>
			</div>
		</div>
	</form>
</template>
