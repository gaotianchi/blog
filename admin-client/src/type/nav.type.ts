export type SidebarItem = {
	title: string;
	bsIconName?: string;
	routeName?: string;
	children?: SidebarItem[];
};

export type BreadcrumbItem = {
	name: string;
	routeName: string;
};
