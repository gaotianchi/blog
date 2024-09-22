import { Node, type NodeViewProps, mergeAttributes } from '@tiptap/core';
import { VueNodeViewRenderer } from '@tiptap/vue-3';
import type { Component } from 'vue';
import LinkNodeView from '../nodeview/LinkNodeView.vue';

declare module '@tiptap/core' {
	interface Commands<ReturnType> {
		LinkExtension: {
			setCustomLink: () => ReturnType;
		};
	}
}

export const LinkExtension = Node.create({
	name: 'LinkExtension',
	group: 'inline',
	inline: true,
	content: 'text*',

	addAttributes() {
		return {
			href: {
				default: '#',
				isRequired: false,
			},
			target: {
				default: '_blank',
				isRequired: false,
			},
			text: {
				default: '链接',
				isRequired: true,
			},
		};
	},

	renderHTML({ HTMLAttributes }) {
		return [
			'a',
			{
				...HTMLAttributes,
				class: 'text-decoration-none',
			},
			HTMLAttributes.text,
			['i', { class: 'bi bi-link-45deg' }, 0],
		];
	},

	addCommands() {
		return {
			setCustomLink:
				() =>
				({ chain, state }) => {
					const { from, to } = state.selection;
					const selectedText = state.doc.textBetween(from, to);
					return (
						chain()
							// .setMark('link')
							.insertContent({
								type: this.name,
								attrs: {
									text: selectedText,
								},
							})
							.run()
					);
				},
		};
	},

	addNodeView() {
		return VueNodeViewRenderer(LinkNodeView as Component<NodeViewProps>);
	},
});
