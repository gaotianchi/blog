import type { RouteParamsGeneric } from 'vue-router';

export type SidebarItem = {
	title: string;
	bsIconName?: string;
	routeName?: string;
	children?: SidebarItem[];
};

export type NavBarItem = {
	title: string;
	bsItemName: string;
	routeName: string;
};

export type BreadcrumbItem = {
	name: string;
	routeName: string;
	params?: RouteParamsGeneric;
};
