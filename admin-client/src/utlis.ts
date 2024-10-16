import { ArticleStatus } from './enum';

export const dataURLToBlob = (dataURL: string) => {
	const byteString = atob(dataURL.split(',')[1]); // 去掉头部的 'data:image/png;base64,'
	const mimeString = dataURL.split(',')[0].split(':')[1].split(';')[0]; // 获取 mime 类型
	const byteArray = new Uint8Array(byteString.length);
	for (let i = 0; i < byteString.length; i++) {
		byteArray[i] = byteString.charCodeAt(i);
	}
	return new Blob([byteArray], { type: mimeString });
};

export const getFormarttedDate = (stringData: string | undefined) => {
	if (!stringData) {
		return null;
	}
	const date = new Date(stringData);
	const formattedDate = date.toLocaleString();
	return formattedDate;
};

export const getArtcicleStatusClass = (articleStatus?: string) => {
	if (!articleStatus) {
		articleStatus = 'TRASH';
	}
	switch (articleStatus.toUpperCase()) {
		case 'PUBLISHED':
			return ArticleStatus.PUBLISHED;
		case 'DRAFT':
			return ArticleStatus.DRAFT;
		default:
			return ArticleStatus.TRASH;
	}
};
