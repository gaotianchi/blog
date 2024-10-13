import type { SidebarItem } from '@/type/nav.type';

export const sidebarDataItems: SidebarItem[] = [
	{
		title: '仪表盘',
		bsIconName: 'bi-speedometer',
		routeName: 'MAIN',
	},
	{
		title: '内容管理',
		bsIconName: 'bi-box-seam-fill',
		children: [
			{
				title: '文章',
				bsIconName: 'bi-file-text',
				routeName: 'ARTICLES',
			},
			{
				title: '图片',
				bsIconName: 'bi-file-earmark-image',
				routeName: 'PICTURE',
			},
		],
	},
	{
		title: '组织',
		bsIconName: 'bi-diagram-2-fill',
		children: [
			{
				title: '标签',
				bsIconName: 'bi-tag',
				routeName: 'TAG',
			},
			{
				title: '系列',
				bsIconName: 'bi-collection',
			},
		],
	},
	{
		title: '评论',
		bsIconName: 'bi-chat-dots-fill',
	},
	{
		title: '订阅者',
		bsIconName: 'bi-person-lines-fill',
	},
	{
		title: '测试',
		bsIconName: 'bi-wrench-adjustable-circle-fill',
		children: [
			{
				title: '空白页',
				bsIconName: 'bi-file-earmark',
				routeName: 'BLANK',
			},
		],
	},
];
