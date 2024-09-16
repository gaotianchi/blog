import type { NavBarItem } from '@/type/nav.type';

export const userNavBarItems: NavBarItem[] = [
	{
		title: '设置',
		bsItemName: 'bi-gear',
		routeName: 'BLOGGER_SETTING',
	},
	{
		title: '退出',
		bsItemName: 'bi-box-arrow-right',
		routeName: 'SIGN_OUT',
	},
];
