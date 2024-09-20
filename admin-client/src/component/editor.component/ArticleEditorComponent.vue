<template>
	<div v-if="editor">
		<FloatingMenu class="floating-menu" :tippy-options="{ duration: 100 }" :editor="editor">
			<div class="btn-group btn-group-sm" role="group" aria-label="Small button group">
				<button
					@click="editor.chain().focus().toggleHeading({ level: 1 }).run()"
					:class="{ 'is-active': editor.isActive('heading', { level: 1 }) }"
					class="btn btn-outline-dark"
				>
					H1
				</button>
				<button @click="openModal" class="btn btn-outline-dark">Quote</button>
			</div>
		</FloatingMenu>
		<EditorContent :editor="editor" />
		<div v-html="htmlContent"></div>
		<ModAlComponentNew
			modal-id="quoteModal"
			ref="quoteModal"
			title="编辑引言"
			@save-change="saveQuote"
		>
			<template #body>
				<div class="row">
					<div class="input-group mb-3">
						<span class="input-group-text">内容</span>
						<textarea
							v-model="quoteText"
							class="form-control"
							aria-label="With textarea"
						></textarea>
					</div>
					<div class="input-group mb-3">
						<span class="input-group-text" id="basic-addon1">来源</span>
						<input
							type="text"
							class="form-control"
							placeholder="Username"
							aria-label="Username"
							aria-describedby="basic-addon1"
							v-model="sourceText"
						/>
					</div>
				</div>
			</template>
		</ModAlComponentNew>
	</div>
</template>
<script setup lang="ts">
	import { ref, onMounted, onBeforeUnmount } from 'vue';
	import StarterKit from '@tiptap/starter-kit';
	import { Editor, EditorContent, FloatingMenu } from '@tiptap/vue-3';
	import { QuoteExtension } from './extensions/QuoteExtension';
	import ModAlComponentNew from '../ModAlComponentNew.vue';
	
	const editor = ref<Editor>();
	const htmlContent = ref('');
	const quoteModal = ref();
	const quoteText = ref('');
	const sourceText = ref('');

	const openModal = () => {
		if (quoteModal.value) {
			quoteModal.value.show();
		}
	};

	const saveQuote = () => {
		editor.value?.commands.setQuote(quoteText.value, sourceText.value);
		quoteModal.value.hide();
	};

	const updateHtmlContent = () => {
		if (editor.value) {
			htmlContent.value = editor.value.getHTML();
		}
	};

	onMounted(() => {
		console.log('...................................');
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
	      <p>这是一个示例段落内容。</p>
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
<style></style>
