<template>
	<NodeViewWrapper
		@mouseenter="handleMouseEnter"
		@mouseleave="handleMouseLeave"
		:style="{ display: 'inline-block', position: 'relative' }"
	>
		<a
			class="text-decoration-none link-text"
			:href="href"
			:target="node.attrs.target"
			ref="linkRef"
		>
			{{ text }}
			<i class="bi bi-link-45deg"></i>
		</a>
		<div v-if="showPanel" class="position-absolute" style="bottom: 1rem">
			<span
				class="fw-light d-inline-block text-truncate outline-dark"
				style="max-width: 300px"
			>
				{{ href }}
			</span>
		</div>
		<div v-if="showPanel" class="position-absolute">
			<div class="hstack">
				<div class="btn-group btn-group-sm" role="group" aria-label="Small button group">
					<button @click="openModal" class="btn btn-outline-dark">
						<i class="bi bi-pencil-square"></i>
					</button>
					<button @click="unLink" class="btn btn-outline-dark">
						<i class="bi bi-fonts"></i>
					</button>
				</div>
			</div>
		</div>
		<ModalComponentNew
			title="编辑链接"
			ref="linkModalRef"
			@save-change="onSaveModal"
		>
			<template #body>
				<div class="form-floating mb-3">
					<input
						class="form-control"
						placeholder="输入链接"
						:id="currentModalId + 'href'"
						v-model="href"
					/>
					<label :for="currentModalId + 'href'">链接</label>
				</div>
				<div class="form-floating mb-3">
					<input
						class="form-control"
						placeholder="输入文本内容"
						:id="currentModalId + 'text'"
						v-model="text"
					/>
					<label :for="currentModalId + 'text'">文本</label>
				</div>
				<div class="form-check form-switch pl-1">
					<input
						class="form-check-input"
						type="checkbox"
						role="switch"
						id="flexSwitchCheckChecked"
						v-model="newTab"
					/>
					<label class="form-check-label" for="flexSwitchCheckChecked">打开新标签</label>
				</div>
			</template>
		</ModalComponentNew>
	</NodeViewWrapper>
</template>
<script setup lang="ts">
	import { nodeViewProps, NodeViewWrapper } from '@tiptap/vue-3';
	import ModalComponentNew from '@/component/ModalComponentNew.vue';
	import { nextTick, onMounted, ref, watch } from 'vue';
	const props = defineProps(nodeViewProps);

	const href = ref<string>(props.node.attrs.href);
	const text = ref<string>(props.node.attrs.text);
	const newTab = ref(true);
	const linkRef = ref<HTMLLinkElement>();
	const showPanel = ref(false);
	const currentModalId = 'modal-' + Math.random();
	const linkModalRef = ref();

	const handleMouseEnter = () => {
		showPanel.value = true;
	};
	const handleMouseLeave = () => {
		showPanel.value = false;
	};
	const openModal = () => {
		linkModalRef.value.show();
	};
	const onSaveModal = () => {
		linkModalRef.value.hide();
	};
	const unLink = () => {
		props.editor.commands.focus();
		if (props.editor.commands.deleteCurrentNode()) {
			console.log('node deleted');
		} else {
			console.log('fail to delete node');
		}
	};
	watch([href, text, newTab], () => {
		updateArri();
	});
	const updateArri = () => {
		props.updateAttributes({
			href: href.value,
			text: text.value,
			target: newTab.value ? '_blank' : '_self',
		});
	};
	onMounted(() => {
		nextTick(() => {
			if (linkModalRef.value) {
				linkModalRef.value.show();
			}
		});
	});
</script>
<style>
	.link-text:hover {
		background-color: rgba(127, 255, 212, 0.516);
	}
</style>
