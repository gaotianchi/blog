<script setup lang="ts">
	import icons from "@/components/icons";
	import { ref, watchEffect, type Ref, reactive } from "vue";
	import { MdEditor } from "md-editor-v3";
	import "md-editor-v3/lib/style.css";
	import Datepicker from "vue3-datepicker";
	import { format } from "date-fns";

	const publishStatus: Ref<boolean> = ref(true);
	const articleTitle: Ref<string> = ref("");
	const macStatus: Ref<boolean> = ref(false);
	const syncStatus: Ref<boolean> = ref(true);
	const articleBody: Ref<string> = ref("");
	const mscStatus: Ref<boolean> = ref(false);
	const activeMscItems: Ref<string[]> = ref([]);
	const tagsInputValue: Ref<string> = ref("hello, world");
	const publishData: Ref<Date> = ref(new Date());
	const articleSlug: Ref<string> = ref(
		"loooooooooooong-long-long-long-article-slug-example"
	);
	const articleSeries: Ref<string> = ref("Example article series title.");
	const seriesOption: Ref<"default" | "select" | "new"> = ref("default");
	const seriesItems = reactive([
		{
			cover: "https://picsum.photos/40",
			title: "Diam ut et nonumy eos minim kasd et",
		},
		{
			cover: "https://picsum.photos/40",
			title: "Diam Te clita et aliquyam ea feugiat at loremm ut amet elitr",
		},
		{
			cover: "https://picsum.photos/40",
			title: "Te clita et aliquyam ea feugiat at lorem ut amet elitra",
		},
		{
			cover: "https://picsum.photos/40",
			title: "Te clita et aliquyam ea feugiat at lorem ut amet elitra",
		},
		{
			cover: "https://picsum.photos/40",
			title: "Te clita et aliquyam ea feugiat at lorem ut amet elitra",
		},
		{
			cover: "https://picsum.photos/40",
			title: "Te clita et aliquyam ea feugiat at lorem ut amet elitra",
		},
	]);
	const previewUrl: Ref<string | null> = ref(null);

	watchEffect(() => {
		articleSlug.value = articleSlug.value.substring(0, 8000);

		articleSeries.value = articleSeries.value.substring(0, 300);
	});

	const permalinkOption: Ref<boolean> = ref(false);
	const fileInput: Ref<HTMLInputElement | null> = ref(null);

	function changeMscItemStatus(item: string): void {
		if (activeMscItems.value.includes(item)) {
			activeMscItems.value = activeMscItems.value.filter(
				(i) => i !== item
			);
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

	function limString(str: string, maxLength: number): string {
		if (str.length <= maxLength) {
			return str;
		} else {
			return str.substring(0, maxLength) + " ...";
		}
	}

	const triggerFileInput = () => {
		fileInput?.value?.click();
	};

	const handleFileUpload = (event: Event) => {
		const selectedFile = (event.target as HTMLInputElement).files?.[0];
		console.log(selectedFile);
		processFile(selectedFile);
	};

	const handleDrop = (event: DragEvent) => {
		event.preventDefault();
		const selectedFile = event.dataTransfer?.files?.[0];
		processFile(selectedFile);
	};

	const processFile = (file: File | undefined) => {
		if (file) {
			console.log("Selected file:", file.name);

			// Read the file and set the previewUrl
			const reader = new FileReader();
			reader.onload = () => {
				previewUrl.value = reader.result as string;
			};
			reader.readAsDataURL(file);

			// You can further process the file or upload it to a server
		}
	};

	const cancelUploadSeriesCover = () => {
		if (fileInput.value) {
			fileInput.value.value = "";
		}

		previewUrl.value = null;
	};
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
					<component
						class="icon"
						v-if="publishStatus"
						:is="icons.update"
					/>
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
					<div class="items-N1jOupAYJx">
						<div class="item-NkzaO6RYJl">
							<button
								type="button"
								class="items-NyM7s6AK1l"
								:class="[{ active: getMscItemStatus('tag') }]"
								@click="changeMscItemStatus('tag')"
							>
								<component
									class="item-VkrD6pCK1l icon smallest"
									:is="icons.down"
									:class="[
										{ active: getMscItemStatus('tag') },
									]"
								/>
								<div class="item-VkrD6pCK1l items-NJ5vAaAFke">
									<div class="item-Ek0lyC0K1g title">
										Tags
									</div>
									<div
										class="item-Ek0lyC0K1g detail"
										v-if="!getMscItemStatus('tag')"
									>
										{{ tagsInputValue }}
									</div>
								</div>
							</button>
							<div
								class="items-N1ys-00Fke"
								v-if="getMscItemStatus('tag')"
							>
								<input
									type="text"
									name="tags"
									id="tags-input-area"
									v-model="tagsInputValue"
								/>
								<div class="items-N15UfCRK1g">
									<div class="item-NkwhmARYke">apple</div>
									<div class="item-NkwhmARYke">grow</div>
								</div>
							</div>
						</div>
						<div class="item-NkzaO6RYJl">
							<button
								type="button"
								class="items-NyM7s6AK1l"
								:class="[
									{ active: getMscItemStatus('datetime') },
								]"
								@click="changeMscItemStatus('datetime')"
							>
								<component
									class="item-VkrD6pCK1l icon smallest"
									:is="icons.down"
									:class="[
										{
											active: getMscItemStatus(
												'datetime'
											),
										},
									]"
								/>
								<div class="items-NJ5vAaAFke">
									<div class="item-Ek0lyC0K1g title">
										Publish date
									</div>
									<div
										class="item-Ek0lyC0K1g detail"
										v-if="!getMscItemStatus('datetime')"
									>
										{{
											format(
												publishData,
												"yyyy-MM-dd HH:mm"
											)
										}}
									</div>
								</div>
							</button>
							<div
								class="items-N1ys-00Fke"
								v-if="getMscItemStatus('datetime')"
							>
								<Datepicker
									:class="'item-jd9eu3ngid'"
									:style="'width: 100%;height:30px;border:none;margin-top:10px;letter-spacing:3px;'"
									v-model="publishData"
									minimumView="time"
									:typeable="true"
									inputFormat="yyyy / MM / dd   HH:mm"
								/>
							</div>
						</div>
						<div class="item-NkzaO6RYJl">
							<button
								type="button"
								class="items-NyM7s6AK1l"
								:class="[{ active: getMscItemStatus('slug') }]"
								@click="changeMscItemStatus('slug')"
							>
								<component
									class="item-VkrD6pCK1l icon smallest"
									:is="icons.down"
									:class="[
										{
											active: getMscItemStatus('slug'),
										},
									]"
								/>
								<div class="items-NJ5vAaAFke">
									<div class="item-Ek0lyC0K1g title">
										Permalink
									</div>
									<div
										class="item-Ek0lyC0K1g detail"
										v-if="!getMscItemStatus('slug')"
									>
										{{
											articleSlug.length <= 35
												? articleSlug
												: articleSlug.substring(0, 35) +
												  " ..."
										}}
									</div>
								</div>
							</button>
							<div
								class="items-N1ys-00Fke"
								v-if="getMscItemStatus('slug')"
							>
								<div class="item-Eyk5Ug15Jl title">
									{{
										"https://gaotianchi.com/" + articleSlug
									}}
								</div>
								<div class="item-Eyk5Ug15Jl items-NJ99_xJ9Jx">
									<div class="item-4ke2_l191x">
										<input
											class="item-NJKMKxkqkx"
											type="radio"
											name="auto-permalink"
											id="auto-permalink"
											v-model="permalinkOption"
											:value="false"
										/>
										<label
											for="auto-permalink"
											class="item-V1J3ixkckg"
											>Auto permalink
										</label>
									</div>
									<div class="item-4ke2_l191x">
										<input
											class="item-NJKMKxkqkx"
											type="radio"
											name="custom-permalink"
											id="custom-permalink"
											v-model="permalinkOption"
											:value="true"
										/>
										<label
											for="custom-permalink"
											class="item-V1J3ixkckg"
											>Custom permalink
										</label>
									</div>
								</div>
								<div
									class="item-Eyk5Ug15Jl"
									v-if="permalinkOption"
								>
									<input
										type="text"
										name="custom-permalink-input"
										id="custom-permalink-input"
										v-model="articleSlug"
										class="item-EkIs0xk9ke"
									/>
									<div class="item-EkIs0xk9ke message">
										<span class="item-VJUWJWJc1x">
											{{
												articleSlug.length.toString() +
												"/" +
												"8000"
											}}
										</span>
									</div>
								</div>
							</div>
						</div>
						<div class="item-NkzaO6RYJl">
							<button
								type="button"
								class="items-NyM7s6AK1l"
								:class="[
									{ active: getMscItemStatus('series') },
								]"
								@click="changeMscItemStatus('series')"
							>
								<component
									class="item-VkrD6pCK1l icon smallest"
									:is="icons.down"
									:class="[
										{
											active: getMscItemStatus('series'),
										},
									]"
								/>
								<div class="items-NJ5vAaAFke">
									<div class="item-Ek0lyC0K1g title">
										Series
									</div>
									<div
										class="item-Ek0lyC0K1g detail"
										v-if="!getMscItemStatus('series')"
									>
										{{ articleSeries }}
									</div>
								</div>
							</button>
							<div
								class="items-N1ys-00Fke"
								v-if="getMscItemStatus('series')"
							>
								<div class="item-Eyk5Ug15Jl title">
									{{
										limString(
											"Current series: " + articleSeries,
											80
										)
									}}
								</div>
								<div class="item-Eyk5Ug15Jl">
									<div class="item-4ke2_l191x">
										<input
											class="item-NJKMKxkqkx"
											type="radio"
											name="default-series"
											id="default-series"
											v-model="seriesOption"
											value="default"
										/>
										<label
											for="default-series"
											class="item-V1J3ixkckg"
											>Default
										</label>
									</div>
									<div class="item-4ke2_l191x">
										<input
											class="item-NJKMKxkqkx"
											type="radio"
											name="select-series"
											id="select-series"
											v-model="seriesOption"
											value="select"
										/>
										<label
											for="select-series"
											class="item-V1J3ixkckg"
											>Choose another
										</label>
									</div>
									<div class="item-4ke2_l191x">
										<input
											class="item-NJKMKxkqkx"
											type="radio"
											name="new-series"
											id="new-series"
											v-model="seriesOption"
											value="new"
										/>
										<label
											for="new-series"
											class="item-V1J3ixkckg"
											>New one
										</label>
									</div>
								</div>
								<div
									class="item-Eyk5Ug15Jl items-NkXzjMk9Jg"
									v-if="seriesOption === 'select'"
								>
									<div
										class="item-EkDocfy5kx items-VyZmjzkqJx"
										v-for="series in seriesItems"
										@click="articleSeries = series.title"
									>
										<div class="item-N1OmiG15Je">
											<img
												:src="series.cover"
												:alt="series.title"
											/>
										</div>
										<span class="item-Nk6QEmkcke">{{
											limString(series.title, 70)
										}}</span>
									</div>
								</div>
								<div
									class="item-Eyk5Ug15Jl items-NkXzjMk9Jg"
									v-if="seriesOption === 'new'"
								>
									<div class="item-NJN4f-gcyx">
										<component
											class="icon item-EkYXMMecJe"
											:is="icons.cancel"
											v-if="previewUrl"
											@click="cancelUploadSeriesCover"
										/>
										<img
											v-if="previewUrl"
											:src="previewUrl"
											alt="Preview"
											class="item-NJrXbMgq1g"
										/>
										<component
											v-else
											:is="icons.uploadImage"
											@click="triggerFileInput"
											@dragover.prevent
											@drop="handleDrop"
										/>
										<input
											type="file"
											name="upload-image"
											id="upload-image"
											style="display: none"
											ref="fileInput"
											@change="handleFileUpload"
										/>
									</div>
									<div class="item-Eyk5Ug15Jl">
										<input
											type="text"
											name="series-title-input"
											id="series-title-input"
											v-model="articleSeries"
											class="item-EkIs0xk9ke"
										/>
										<div class="item-EkIs0xk9ke message">
											<span class="item-VJUWJWJc1x">
												{{
													articleSeries.length.toString() +
													"/" +
													"300"
												}}
											</span>
										</div>
									</div>
								</div>
							</div>
						</div>
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

	#md-editor-v3 {
		min-height: calc(100vh - 51px);
	}

	/* ================================ */
	/* ================================ */
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

	.msc-title {
		width: 100%;
		margin-bottom: 10px;
		font-size: 1rem;
		font-weight: 700;
	}

	.items-N1jOupAYJx {
		width: 100%;
	}

	.item-NkzaO6RYJl {
		width: 100%;
		min-height: 40px;
		margin-bottom: 20px;
		border-bottom: rgba(128, 128, 128, 0.514) solid 1px;
	}

	button.items-NyM7s6AK1l {
		width: 100%;
		display: flex;
		justify-content: flex-start;
		align-items: center;
		cursor: pointer;
		border: none;
		background-color: white;
	}

	.item-VkrD6pCK1l.icon {
		transform: rotate(-90deg);
		margin-right: 10px;
		transition: all 0.3s ease;
	}

	.item-VkrD6pCK1l.icon.active {
		transform: none;
	}

	.items-NJ5vAaAFke {
		flex-grow: 1;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: flex-start;
	}

	.item-Ek0lyC0K1g.title {
		font-size: 1rem;
		font-weight: 400;
		text-align: left;
	}

	.item-Ek0lyC0K1g.detail {
		font-size: smaller;
		font-style: italic;
		text-align: left;
	}

	.items-N1ys-00Fke {
		width: 100%;
		display: flex;
		flex-direction: column;
	}

	#tags-input-area {
		width: 100%;
		height: 20px;
		margin-top: 10px;
		font-size: 1rem;
		border: none;
		outline: none;
		border-bottom: rgba(250, 187, 71, 0.514) solid 1px;
	}

	#tags-input-area:focus-visible {
		border: none;
		outline: none;
		border-bottom: rgba(250, 71, 223, 0.514) solid 1px;
	}

	.items-N15UfCRK1g {
		width: 100%;
		min-height: 150px;
		display: flex;
		flex-direction: column;
		justify-content: flex-start;
		align-items: flex-start;
		padding: 0 15px;
	}

	.item-NkwhmARYke {
		width: 100%;
		padding: 4px 0;
		cursor: pointer;
		border-bottom: rgba(128, 128, 128, 0.514) solid 1px;
		font-weight: 700;
	}

	.item-jd9eu3ngid:focus-visible {
		outline: none;
	}

	.item-Eyk5Ug15Jl {
		margin-top: 10px;
		overflow-x: hidden;
	}

	.item-Eyk5Ug15Jl.title {
		font-size: small;
		color: grey;
	}

	.item-4ke2_l191x {
		width: 100%;
		height: 25px;
		padding-left: 20px;
		display: flex;
		align-items: center;
	}

	.item-NJKMKxkqkx {
		width: 16px;
		height: 16px;
	}

	.item-V1J3ixkckg {
		padding-left: 10px;

		font-size: small;
	}

	.item-EkIs0xk9ke {
		width: 100%;
		height: 25px;

		border: none;
		outline: none;
	}

	input.item-EkIs0xk9ke {
		border-bottom: rgba(231, 155, 55, 0.514) solid 1px;
	}

	input.item-EkIs0xk9ke:focus-visible {
		outline: none;
	}

	.item-EkIs0xk9ke.message {
		display: flex;
		justify-content: flex-end;
		align-items: center;
	}

	.item-VJUWJWJc1x {
		font-size: smaller;
		font-style: italic;
		letter-spacing: 2px;
		color: #6d6969;
	}

	.items-NkXzjMk9Jg {
		width: 100%;
		max-height: 260px;
	}

	.item-EkDocfy5kx {
		width: 100%;
		cursor: pointer;
	}

	.item-EkDocfy5kx:hover {
		background-color: rgb(222, 222, 222);
	}

	.items-VyZmjzkqJx {
		display: flex;
		align-items: center;
		margin: 2px 0;
		padding: 3px 0;
	}

	.item-N1OmiG15Je {
		width: 40px;
		height: 40px;
	}

	.item-Nk6QEmkcke {
		margin-left: 10px;
		font-size: small;
		color: #6d6969;
	}

	.item-NJN4f-gcyx {
		position: relative;
		width: 100%;

		display: flex;
		justify-content: center;
		align-items: center;

		cursor: pointer;
	}

	.item-EkYXMMecJe {
		position: absolute;
		top: 0;
		right: 0;

		padding: 5px;
		background-color: rgb(249, 249, 249);
	}

	.item-EkYXMMecJe:hover {
		background-color: #a9a2a2;
	}

	.item-NJrXbMgq1g {
		max-width: 100%;
		max-height: 100px;
	}
</style>
