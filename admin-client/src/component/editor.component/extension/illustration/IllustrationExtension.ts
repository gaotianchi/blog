import { Node, type NodeViewProps } from '@tiptap/core';
import IllustrationNodeView from './IllustrationNodeView.vue';
import { VueNodeViewRenderer } from '@tiptap/vue-3';
import type { Component } from 'vue';
import { type ImageOptions, Image } from '@tiptap/extension-image';

declare module '@tiptap/core' {
	interface Commands<ReturnType> {
		ImageExtension: {
			setImage: () => ReturnType;
		};
	}
}

export const ImageExtension = Image.extend<ImageOptions>({
	name: 'ImageExtension',

	addAttributes() {
		return {
			...this.parent?.(),
			align: {
				default: 'float-none',
			},
			imageId: {
				default: null,
			},
		};
	},
	addNodeView() {
		return VueNodeViewRenderer(IllustrationNodeView as Component<NodeViewProps>);
	},
	addCommands() {
		return {
			setImage:
				() =>
				({ commands }) => {
					return commands.insertContent({
						type: this.name,
					});
				},
		};
	},
});
