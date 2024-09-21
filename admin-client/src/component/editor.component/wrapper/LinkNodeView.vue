<template>
	<NodeViewWrapper :style="{ display: 'inline-block' }">
		<a
			class="text-decoration-none link-text position-relative"
			:href="href"
			@mouseenter="handleMouseEnter"
			@mouseleave="handleMouseLeave"
			:target="target"
			ref="linkRef"
		>
			{{ text }}
			<i class="bi bi-link-45deg"></i>
			<div v-if="showPanel" class="position-absolute top-0 end-0">
				<a name="" id="" class="btn btn-primary" href="#" role="button">Button</a>
			</div>
		</a>
	</NodeViewWrapper>
</template>
<script setup lang="ts">
	import { nodeViewProps, NodeViewWrapper } from '@tiptap/vue-3';
	import ModalComponentNew from '@/component/ModalComponentNew.vue';
	import { onBeforeUnmount, onMounted, ref, watch } from 'vue';
	const props = defineProps(nodeViewProps);

	const href = ref(props.node.attrs.href);
	const text = ref(props.node.attrs.text);
	const target = ref(props.node.attrs.target);
	const linkRef = ref<HTMLLinkElement>();
	const showPanel = ref(false);

	const handleMouseEnter = () => {
		showPanel.value = true;
	};
	const handleMouseLeave = () => {
		showPanel.value = false;
	};
	watch([href, text, target], () => {
		updateArri();
	});

	const updateArri = () => {
		props.updateAttributes({
			href: href.value,
			text: text.value,
			target: target.value,
		});
	};
</script>
<style>
	.link-text:hover {
		background-color: rgba(127, 255, 212, 0.516);
	}
</style>
