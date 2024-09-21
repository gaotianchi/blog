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
					</div>
					<div
						class="btn-group btn-group-sm"
						role="group"
						aria-label="Small button group"
					>
						<BubbleMenu :editor="editor" :tippy-options="{ duration: 100 }">
							<button
								@click="setLink"
								:class="{ 'is-active': editor.isActive('link') }"
								class="btn btn-outline-dark"
							>
								set link
							</button>
							<button
								@click="editor.chain().focus().unsetLink().run()"
								:disabled="!editor.isActive('link')"
							>
								Unset link
							</button>
						</BubbleMenu>
					</div>
				</FloatingMenu>
				<EditorContent :editor="editor" />
			</div>
		</div>
		<div class="col p-3"><div v-html="htmlContent"></div></div>
	</div>
</template>

<script setup lang="ts">
	import StarterKit from '@tiptap/starter-kit';
	import { Editor, EditorContent, FloatingMenu, BubbleMenu } from '@tiptap/vue-3';
	import { ref, onMounted, onBeforeUnmount } from 'vue';
	import Link from '@tiptap/extension-link';
	const editor = ref<Editor>();
	const htmlContent = ref('');
	const updateHtmlContent = () => {
		if (editor.value) {
			htmlContent.value = editor.value.getHTML();
		}
	};

	const setLink = () => {
		if (editor.value) {
			const previousUrl = editor.value.getAttributes('link').href;
			console.log(editor.value);
			const url = window.prompt('URL', previousUrl);

			// cancelled
			if (url === null) {
				return;
			}

			// empty
			if (url === '') {
				editor.value.chain().focus().extendMarkRange('link').unsetLink().run();

				return;
			}

			// update link
			editor.value.chain().focus().extendMarkRange('link').setLink({ href: url }).run();
		}
	};

	onMounted(() => {
		editor.value = new Editor({
			extensions: [
				StarterKit.configure({
					blockquote: {
						HTMLAttributes: {
							class: 'blockquote',
						},
					},
				}),
				Link.configure({
					openOnClick: false,
					defaultProtocol: 'https',
				}),
			],
			content: `
	              <p>
          This is still the text editor you’re used to, but enriched with node views.
        </p>
        <p>
          Did you see that? That’s a Vue component. We are really living in the future.
        </p>
	    `,
			editorProps: {
				attributes: {
					class: 'editor-content border-0 p-3 vh-100 focus:outline-none',
				},
			},
		});
		editor.value.on('update', updateHtmlContent);
		htmlContent.value = editor.value.getHTML();
	});
	onBeforeUnmount(() => {
		if (editor.value) {
			editor.value.destroy();
		}
	});
</script>
