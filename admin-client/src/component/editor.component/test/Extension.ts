import { mergeAttributes, Node, type NodeViewProps } from '@tiptap/core';
import { VueNodeViewRenderer } from '@tiptap/vue-3';
import Wrapper from './Wrapper.vue';
import type { Component } from 'vue';

export default Node.create({
	name: 'vueComponent',
	group: 'block',
	atom: true,
	addAttributes() {
		return {
			count: {
				default: 0,
			},
		};
	},
	parseHTML() {
		return [
			{
				tag: 'vue-component',
			},
		];
	},
	renderHTML({ HTMLAttributes }) {
		return ['vue-component', mergeAttributes(HTMLAttributes)];
	},

	addNodeView() {
		return VueNodeViewRenderer(Wrapper as Component<NodeViewProps>);
	},
});
