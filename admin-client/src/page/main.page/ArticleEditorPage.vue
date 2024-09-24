<template>
	<MainPageHeaderComponent
		title="编辑内容详情"
		sub-title="编辑文章的具体信息，包括标题、正文、标签等"
		bs-icon-name="bi-pencil-fill"
		:breadcrumb-items="[
			{
				name: '管理文章',
				routeName: 'ARTICLES',
			},
			{
				name: '编辑内容详情',
				routeName: 'ARTICLE_EDITOR',
				params: {
					articleId: articleId,
				},
			},
		]"
	></MainPageHeaderComponent>

	<div class="row">
		<div v-if="editor" class="container" style="height: fit-content">
			<FloatingMenu class="floating-menu" :tippy-options="{ duration: 100 }" :editor="editor">
				<div class="btn-group btn-group-sm" role="group" aria-label="Small button group">
					<button
						@click="editor.chain().focus().toggleHeading({ level: 1 }).run()"
						:class="{ 'is-active': editor.isActive('heading', { level: 1 }) }"
						class="btn btn-outline-dark"
					>
						H1
					</button>
					<button @click="editor.commands.setQuoteBlock()" class="btn btn-outline-dark">
						<i class="bi bi-quote"></i>
					</button>
					<button @click="editor.commands.setImage()" class="btn btn-outline-dark">
						<i class="bi bi-image"></i>
					</button>
				</div>
			</FloatingMenu>
			<BubbleMenu :editor="editor" class="bubble-menu" :tippy-options="{ duration: 100 }">
				<div class="btn-group btn-group-sm" role="group" aria-label="Small button group">
					<button @click="editor.commands.setCustomLink()" class="btn btn-outline-dark">
						<i class="bi bi-link"></i>
					</button>
				</div>
			</BubbleMenu>
			<EditorContent :editor="editor" />
		</div>
	</div>
	<div class="row">bottom</div>
</template>
<script setup lang="ts">
	import { ref, onMounted, onBeforeUnmount } from 'vue';
	import StarterKit from '@tiptap/starter-kit';
	import { Editor, EditorContent, FloatingMenu, BubbleMenu } from '@tiptap/vue-3';
	import { QuoteExtension } from '@/component/editor.component/extension/QuoteExtension';
	import { LinkExtension } from '@/component/editor.component/extension/LinkExtension';
	import { ImageExtension } from '@/component/editor.component/extension/ImageExtension';
	import MainPageHeaderComponent from '@/component/MainPageHeaderComponent.vue';
	const props = defineProps<{
		articleId: string;
	}>();
	const editor = ref<Editor>();
	const htmlContent = ref('');

	onMounted(() => {
		editor.value = new Editor({
			extensions: [
				ImageExtension,
				LinkExtension,
				QuoteExtension,
				StarterKit.configure({
					blockquote: {
						HTMLAttributes: {
							class: 'blockquote',
						},
					},
				}),
			],
			content: `
		    `,
			editorProps: {
				attributes: {
					class: 'editor-content border-0 p-3 focus:outline-none',
				},
			},
		});
		// editor.value.on('update', updateHtmlContent);
		htmlContent.value = editor.value.getHTML();
	});

	onBeforeUnmount(() => {
		if (editor.value) {
			editor.value.destroy();
		}
	});
	onMounted(() => {
		console.log(props.articleId);
	});
</script>

<style>
	.tiptap {
		min-height: calc(100vh - 250px);
	}
</style>
