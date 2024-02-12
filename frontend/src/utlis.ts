export function isShallowEqual(a: any, b: any): boolean {
	const aProps = Object.getOwnPropertyNames(a);
	const bProps = Object.getOwnPropertyNames(b);

	if (aProps.length !== bProps.length) {
		return false;
	}

	for (let i = 0; i < aProps.length; i++) {
		const propName = aProps[i];

		const propA = a[propName];
		const propB = b[propName];

		if (typeof propA === "object") {
			if (!isShallowEqual(propA, propB)) {
				return false;
			}
		} else if (propA !== propB) {
			return false;
		}
	}

	return true;
}
