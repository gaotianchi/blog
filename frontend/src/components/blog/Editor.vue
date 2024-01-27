<script setup lang="ts">
import icons from "@/components/icons";
import { ref, type Ref } from "vue";
import { MdEditor } from "md-editor-v3";
import "md-editor-v3/lib/style.css";

const publishStatus: Ref<boolean> = ref(true);
const articleTitle: Ref<string> = ref("");
const macStatus: Ref<boolean> = ref(false);
const syncStatus: Ref<boolean> = ref(true);
const articleBody: Ref<string> = ref("");
const mscStatus: Ref<boolean> = ref(false);
const activeMscItems: Ref<string[]> = ref([]);

function changeMscItemStatus(item: string): void {
	if (activeMscItems.value.includes(item)) {
		activeMscItems.value = activeMscItems.value.filter((i) => {
			i != item;
		});
	} else {
		activeMscItems.value.push(item);
	}
}

function getMscItemStatus(item: string): boolean {
	if (activeMscItems.value.includes(item)) {
		return true;
	} else {
		return false;
	}
}
</script>

<template>
	<div class="a-title-act-btn-header">
		<div class="att-article-title">
			<div class="at-tip">Article title</div>
			<div class="att-input-area">
				<input
					type="text"
					name="article-title"
					id="article-title"
					v-model="articleTitle"
				/>
				<div class="at-underline"></div>
			</div>
		</div>
		<div class="sync-status-reporter">
			<component class="icon" v-if="syncStatus" :is="icons.sync" />
			<component class="icon" v-else :is="icons.unsync" />
		</div>
		<div class="action-btns-cont">
			<div class="abs-main-one">
				<button type="button" class="amo-mainbtn amobtn">
					<component class="icon" v-if="publishStatus" :is="icons.update" />
					<component class="icon" v-else :is="icons.publish" />

					<span class="abc-text">
						{{ publishStatus ? "Update" : "Publish" }}
					</span>
				</button>
				<button
					type="button"
					class="amo-morebtn amobtn"
					@mouseenter="macStatus = true"
					@mouseleave="macStatus = false"
				>
					<component
						class="icon small icon-down"
						:is="icons.down"
						:class="[{ active: macStatus }]"
					/>
					<div class="more-actions-container" v-if="macStatus">
						<div class="mac-item">
							<component class="icon" :is="icons.view" />
							<span class="maci-text">Preview blog post</span>
						</div>
						<div class="mac-item" v-if="publishStatus">
							<component class="icon" :is="icons.draft" />
							<span class="maci-text">Revert to draft</span>
						</div>
					</div>
				</button>
			</div>
			<div class="abs-main-two">
				<button
					type="button"
					class="amo-settingbtn amobtn"
					@click="mscStatus = !mscStatus"
				>
					<component class="icon small" :is="icons.setting" />
				</button>
				<div class="more-setting-container" v-if="mscStatus">
					<div class="msc-title">Article setting</div>
					<div class="msc-btns">
						<button
							type="button"
							class="msc-item"
							:class="[{ active: getMscItemStatus('tag') }]"
							@click="changeMscItemStatus('tag')"
						>
							<component
								class="icon smallest msci-icon"
								:is="icons.down"
								:class="[{ active: getMscItemStatus('tag') }]"
							/>
                            <div class="mscbt-container">
                                <div class="mscb-title">Tags</div>
                                <div class="mscb-detail" v-if="!getMscItemStatus('tag')">hello, world</div>
                            </div>
                            
						</button>
						<div class="msc-item detail" v-if="getMscItemStatus('tag')"></div>
                        <div class="msci-underline"></div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<MdEditor v-model="articleBody" />
</template>

<style scoped>
.a-title-act-btn-header {
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.att-article-title {
	display: flex;
	flex-direction: column;
	align-items: flex-start;
	justify-content: space-between;

	flex-grow: 1;
}

.at-tip {
	padding: 3px;

	color: rgba(31, 35, 35, 0.29);
	font-size: 13px;
}

.att-input-area {
	width: 100%;
}

#article-title {
	width: 100%;

	padding: 0 5px;

	border: none;
	outline: none;

	font-size: 1.3rem;
	font-weight: 500;
}

#article-title:focus,
#article-title:focus-visible {
	border: none;
	outline: none;
}

.at-underline {
	outline: #ffa33c solid 1px;
}

.action-btns-cont {
	position: relative;
	width: 180px;
	height: 50px;

	display: flex;
	justify-content: space-between;
	align-items: center;
}

.abs-main-one {
	position: relative;

	display: flex;
}

.abs-main-two {
	position: relative;

	display: flex;
}

.amobtn {
	height: 40px;

	cursor: pointer;

	border: none;
	border-radius: 5px;
}

.amo-mainbtn {
	display: flex;
	justify-content: center;
	align-items: center;

	background-color: #ffa33c;
}

.abc-text {
	padding-left: 5px;

	color: white;
}

.amo-morebtn {
	position: relative;
	width: 35px;

	display: flex;
	justify-content: center;
	align-items: center;

	border: #c4c4c4 solid 1px;
	background-color: #b15eff;
}

.more-actions-container {
	position: absolute;
	top: 39px;
	right: -35px;
	z-index: 2;

	padding: 15px 0;

	box-shadow: -3px 3px 4px 0px rgba(0, 0, 0, 0.25);
	background-color: white;
}

.mac-item {
	width: 230px;
	height: 40px;

	display: flex;
	justify-content: flex-start;
	align-items: center;

	padding: 4px 25px;

	cursor: pointer;
}

.mac-item:hover {
	background-color: rgb(222, 222, 222);
}

.maci-text {
	padding-left: 10px;
}

.icon-down {
	transform: rotate(90deg);

	transition: all 0.3s ease;
}

.icon-down.active {
	transform: none;
}

.sync-status-reporter {
	padding: 0 5px;
}

.amo-settingbtn {
	position: relative;
	width: 35px;

	display: flex;
	justify-content: center;
	align-items: center;
}

.more-setting-container {
	position: absolute;
	top: 46px;
	right: 0;
	z-index: 1;

	width: 300px;
	min-height: calc(100vh - 51px);

    padding: 10px 20px;

	box-shadow: -3px 3px 4px 0px rgba(0, 0, 0, 0.25);
	background-color: white;
}

#md-editor-v3 {
	min-height: calc(100vh - 51px);
}

.msc-title {
    width: 100%;

    margin-bottom: 10px;

    font-size: 1rem;
    font-weight: 700;
}

.msc-item {
    width: 100%;
    min-height: 40px;

    display: flex;
    justify-content: flex-start;
    align-items: center;

    border: none;
    background-color: white;
}

.msci-underline {
    border-top: rgba(128, 128, 128, 0.514) solid 1px;
}

.msci-icon {
    transform: rotate(-90deg);

    margin-right: 10px;

    transition: all 0.3s ease;
}

.msci-icon.active {
    transform: none;
}

.mscbt-container {
    flex-grow: 1;

    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: flex-start;
}

.mscb-title {
    font-size: 1rem;
    font-weight: 400;
}

.mscb-detail {
    font-size: smaller;
    font-style: italic;
}
</style>
