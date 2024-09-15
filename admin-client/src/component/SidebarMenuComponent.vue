<template>
	<ul class="app-menu">
		<li
			v-for="(sidebarDataItem, index) in sidebarDataItems"
			@click="toggleTreeview(index)"
			:class="[
				{ treeview: sidebarDataItem.children },
				{ 'is-expanded': expandedIndex === index },
			]"
		>
			<RouterLink
				class="app-menu__item"
				:to="{ name: sidebarDataItem.routeName }"
				data-toggle="treeview"
			>
				<i class="app-menu__icon bi" :class="sidebarDataItem.bsIconName"></i>
				<span class="app-menu__label">{{ sidebarDataItem.title }}</span>
				<i
					class="treeview-indicator bi"
					:class="{ 'bi-chevron-right': sidebarDataItem.children }"
				></i>
			</RouterLink>
			<ul
				v-if="sidebarDataItem.children"
				class="treeview-menu"
				@click="toggleTreeview(index)"
			>
				<li v-for="subItem in sidebarDataItem.children">
					<RouterLink class="treeview-item" :to="{ name: subItem.routeName }">
						<i
							class="icon bi"
							:class="subItem.bsIconName ? subItem.bsIconName : 'bi-circle-fill'"
						></i>
						{{ subItem.title }}
					</RouterLink>
				</li>
			</ul>
		</li>
	</ul>
</template>

<script setup lang="ts">
	import { sidebarDataItems } from '@/const/sidebar.const';
	import { ref } from 'vue';
	const expandedIndex = ref(-1);

	const toggleTreeview = (index: number) => {
		// 如果点击的项已经是展开状态，则收起它
		if (expandedIndex.value === index) {
			expandedIndex.value = -1;
		} else {
			// 否则展开点击的项，收起其他项
			expandedIndex.value = index;
		}
	};
</script>
