import { Node, type NodeViewProps } from '@tiptap/core';
import IllustrationNodeView from './IllustrationNodeView.vue';
import { VueNodeViewRenderer } from '@tiptap/vue-3';
import type { Component } from 'vue';
import { type ImageOptions, Image } from '@tiptap/extension-image';

declare module '@tiptap/core' {
	interface Commands<ReturnType> {
		Illustration: {
			setIllustration: () => ReturnType;
		};
	}
}

export const Illustration = Image.extend<ImageOptions>({
	name: 'Illustration',

	addAttributes() {
		return {
			...this.parent?.(),
			align: {
				default: 'float-none',
			},
			id: {
				default: null,
			},
		};
	},
	addNodeView() {
		return VueNodeViewRenderer(IllustrationNodeView as Component<NodeViewProps>);
	},
	addCommands() {
		return {
			setIllustration:
				() =>
				({ commands }) => {
					return commands.insertContent({
						type: this.name,
					});
				},
		};
	},
});
