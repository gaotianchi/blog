import { createApp } from 'vue';
import AlertComponent from '@/component/AlertComponent.vue';

const showMessage = (message: string, type: string) => {
	// 创建组件实例
	const alertInstance = createApp(AlertComponent, {
		message,
		type,
	});

	// 创建一个 DOM 元素并将其添加到 body 中
	const container = document.createElement('div');
	document.body.appendChild(container);

	// 将组件挂载到该 DOM 元素
	alertInstance.mount(container);

	// 通过 requestAnimationFrame 异步执行过渡效果
	requestAnimationFrame(() => {
		// 设置显示时长为 3 秒
		setTimeout(() => {
			// 添加过渡效果，避免影响用户操作
			container.style.transition = 'opacity 0.5s';
			container.style.opacity = '0';

			// 等待过渡完成后卸载组件
			setTimeout(() => {
				alertInstance.unmount();
				document.body.removeChild(container);
			}, 500); // 过渡效果持续 0.5 秒后销毁组件
		}, 3000); // 3秒后开始过渡效果
	});
};

export default showMessage;
