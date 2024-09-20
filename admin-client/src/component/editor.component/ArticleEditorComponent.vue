<template>
	<div class="row">
		<div class="col">
			<div v-if="editor">
				<FloatingMenu
					class="floating-menu"
					:tippy-options="{ duration: 100 }"
					:editor="editor"
				>
					<div
						class="btn-group btn-group-sm"
						role="group"
						aria-label="Small button group"
					>
						<button
							@click="editor.chain().focus().toggleHeading({ level: 1 }).run()"
							:class="{ 'is-active': editor.isActive('heading', { level: 1 }) }"
							class="btn btn-outline-dark"
						>
							H1
						</button>
						<button
							@click="editor.commands.setQuoteBlock()"
							class="btn btn-outline-dark"
						>
							Quote
						</button>
					</div>
				</FloatingMenu>
				<EditorContent :editor="editor" />
			</div>
		</div>
		<div class="col p-3"><div v-html="htmlContent"></div></div>
	</div>
</template>
<script setup lang="ts">
	import { ref, onMounted, onBeforeUnmount } from 'vue';
	import StarterKit from '@tiptap/starter-kit';
	import { Editor, EditorContent, FloatingMenu } from '@tiptap/vue-3';
	import { QuoteExtension } from './extension/QuoteExtension';

	const editor = ref<Editor>();
	const htmlContent = ref('');

	const updateHtmlContent = () => {
		if (editor.value) {
			htmlContent.value = editor.value.getHTML();
		}
	};

	onMounted(() => {
		editor.value = new Editor({
			extensions: [
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
					class: 'editor-content border-0 p-3 vh-100 focus:outline-none',
				},
			},
		});
		console.log(editor.value);

		editor.value.on('update', updateHtmlContent);
		htmlContent.value = editor.value.getHTML();
	});

	onBeforeUnmount(() => {
		if (editor.value) {
			editor.value.destroy();
		}
	});
</script>
<style>
	.tiptap:focus,
	input:focus {
		outline: none;
	}
</style>
