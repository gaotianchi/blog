<script setup lang="ts">
	import { reactive } from "vue";
	import type { Article } from "@/typing";
	import { propConfirm, propMessage, localArticle } from "@/api/local";
	import { remoteArticle } from "@/api/remote";
	import icons from "@/components/icons";
	import SettingBar from "./SettingBar.vue";
</script>
<template>
	<div class="parent-NJU0QvKikx">
		<input
			type="text"
			name="article-title"
			id="article-title"
			placeholder="Article title 100 words or less."
			aria-label="article-ttile"
		/>
		<div class="parent-Vk3CQPKoJg">
			<component
				v-if="true"
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
				<button type="button" class="parent-EJWZ4DKoJe">
					<component
						:is="icons.setting"
						class="parent-Ny-WMwFjJx icon"
					/>
				</button>
				<div class="parent-V1RmedFs1x">
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
				<button
					type="button"
					class="parent-4yuzEwtsJx child-Ek1DzDYjke"
				>
					<component
						:is="icons.down"
						class="parent-E1mV4vKj1g icon"
					/>
				</button>
			</div>
			<button type="button" class="parent-VJ2zVvYsyg">
				<component :is="icons.publish" class="parent-41VU4DYoke icon" />
				<span class="parent-4ygQ4PKikg">Publish</span>
			</button>
		</div>
	</div>
</template>
<style scoped>
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
		z-index: 9999;
		width: 320px;
		height: calc(100vh - 120px);
		padding: 10px;
		top: 50px;
		right: -148px;
		box-shadow: 0px 1px 3px 0px rgba(0, 0, 0, 0.25);
		background: rgb(255, 255, 255);
		overflow-y: auto;
	}
	.parent-Nk360OYjke {
		margin-bottom: 10px;
		font-weight: 500;
		padding-left: 6px;
		color: grey;
	}
</style>
