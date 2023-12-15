	<template>
	<view class="my">
		<view class="container">
			<!-- User Section -->

			<view class="background">
				<view class="user-section">
					<image src="/static/a02942b44ed0754b2f366f1f41923ee0.jpeg" class="profile-img"></image>
					<text class="username">{{ user.name }}</text>
				</view>


				<!-- Data Section -->
				<view class="data-show">
					<text class="donation-data-title" style="color: white; ">捐赠数据</text>

					<!-- Help Number and Total Donation Amount -->
					<view class="grid">
						<!-- Help Number -->
						<view class="grid-item">
							<image src="/static/img/帮助次数.png" class="icon"></image>
							<view class="data-info">
								<text class="data-number">{{ user.help_num }}</text>
								<text class="data-label">帮助次数</text>
							</view>
						</view>

						<!-- Total Donation Amount -->
						<view class="grid-item">
							<image src="/static/img/捐助金额.png" class="icon"></image>
							<view class="data-info">
								<text class="data-number">{{ user.sumtotal }}</text>
								<text class="data-label">捐助总金额</text>
							</view>
						</view>
					</view>
				</view>
			</view>
		</view>

		<view class="box2">
			<view class="helped-children">
				<h3 style="color: orange;">&nbsp;&nbsp;我的累计捐赠</h3>

				<view v-for="child in user.children" :key="child" class="child">
					<p class="child-text">
					<div class="item">
						<image :src="child.image" mode="" style="height: 50px; width: 50px; border-radius: 50%;">
						</image>
						<div
							style="margin-top: 10px; margin-left: 10px; font-size: 20px; font-weight: bold;color: #553311;">
							{{ child.name }}&nbsp;&nbsp;{{child.age}}岁&nbsp;&nbsp;{{child.category}}
						</div>

					</div>
					<div class="bubble">
						<span class="red-text">捐献了{{ child.money }}元，用于为孩子购置{{child.item}}
							<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							{{child.data}}</span>
					</div>
					<div style="display: flex; justify-content: center;">
						<tui-button class="jzbutton" type="warning" shape="circle" width="270px" height="40px" style="font-weight: bold;"
							@tap="gotoCertificate(child)">
							查看捐赠证书
						</tui-button>
					</div>

					</p>
				</view>
			</view>
		</view>

	</view>
</template>



<script>
	import tuiButton from "@/components/thorui/tui-button/tui-button.vue"
	import tuiCard from "@/components/thorui/tui-card/tui-card.vue"

	export default {
		components: {
			tuiButton,
			tuiCard
		},
		data() {
			return {
				user: {
					id: '',
					name: '',
					help_num: 0,
					sumtotal: 0,
					children: [
						// {
					// 		id: 7,
					// 		name: '张小猪',
					// 		age: 10,
					// 		category: '云南',
					// 		image: '/static/img/kid1.png',
					// 		money: 3800,
					// 		data: '2021/12/21'
					// 	},
					// 	{
					// 		id: 8,
					// 		name: '林可可',
					// 		age: 8,
					// 		category: '甘肃',
					// 		image: '/static/img/kid3.png',
					// 		money: 300,
					// 		data: '2021/12/03'
					// 	},
						// {
						// 	id: 9,
						// 	name: '网名',
						// 	age: 12,
						// 	category: '甘肃',
						// 	image: '/static/img/网名.jpg',
						// 	money: 2740,
						// 	data: '2021/11/21'
						// }
					]
				},
				children: {
					id: '',
					name: '',
					age: '',
					category: '',
					image: '',
					money: '',
					data: '',
					item: ''
				}

			};
		},
		mounted() {
			// 组件挂载后立即调用搜索，以初始化视图
			this.fetchData()
		},
		methods: {
			gotoCertificate(child) {
				const donateData = encodeURIComponent(JSON.stringify(child));
				uni.navigateTo({
					url: '/pages/certificate/certificate?data=' + donateData
				});
			},
			async fetchData() {
				const result = await uni.request({
					url: "http://localhost:8085/user/getPersonCenter?id=1",
					method: 'GET',
					header: {
						'Content-Type': 'application/json',
					}
				})
				// console.log(result)
				//根据查表组建视图
				if (result.statusCode == 200) {
					var responseData = result.data;
					console.log(result)
					var dChild = [];
					// var array=[];
					// console.log(responseData.message.Children);
					var array = Array.from(responseData.message.children);
					// console.log(array)
					for (let i = 0; i < array.length; i++) {
						let kidona = {
							id: array[i].id,
							name: array[i].name,
							age: array[i].age,
							category: array[i].category,
							image: array[i].image,
							money: array[i].money,
							data: array[i].data,
							item: array[i].item
						}
						dChild.push(kidona);
						console.log(dChild)
					}
					let item = {
						id: responseData.message.id,
						name: responseData.message.name,
						help_num: responseData.message.help_num,
						sumtotal: responseData.message.sumtotal,
						children: dChild
					};
					this.user = item;
					console.log(this.user)
				} else {
					console.log('ERROR')
				}
			}
		}
	}
</script>

<style lang="scss">
	.child-container {
		display: flex;
		justify-content: left;
	}

	.my {
		background-color: #fcf3d4;
	}

	.container {
		// background-color: #f4f6fa;
		background-image: linear-gradient(to bottom, #fa804e, #fba87c, #fcf3d4);
		border-radius: 0 0 0px 0px;
		/* 左下和右下圆角，上边和左边为直角 */
		padding: 10px;
		height: 350rpx;
		display: flex;
		flex-direction: column;
		justify-content: space-between;
		height: 100%;
	}

	.background {
		// background-image: url("/static/img/背景2.jfif");
		background-size: cover;
		height: 250px;
		// padding: 20px;

	}

	.user-section {
		display: flex;
		flex-direction: column;
		align-items: center;
		margin-top: 20px;
		/* This sets the distance from the top */
	}

	.profile-img {
		width: 100px;
		/* Adjust as necessary */
		height: 100px;
		/* Adjust as necessary */
		border-radius: 50%;
	}

	.username {
		font-size: 50px;
		/* Larger than the numbers in the boxes */
		color: #ffffff;
		margin-top: 10px;
		/* This sets the distance from the image to the name */
		font-size: 16px;
		/* Adjust as necessary */
		font-weight: bold;
	}

	.data-show {
		flex-grow: 1;
		display: flex;
		flex-direction: column;
		justify-content: flex-end;
		/* Aligns the data to the bottom */
		// padding-bottom: 1px;
		/* This sets the distance from the bottom */
	}

	.donation-data-title {
		font-size: 20px;
		/* Larger than the numbers in the boxes */
		color: #555353;
		text-align: left;
		margin-left: 10px;
		margin-bottom: 1px;
		/* Space above the grid */
		margin-top: 5px;
	}

	.grid {
		display: flex;
	}

	.grid-item {
		display: flex;
		flex-direction: row;
		align-items: center;
		justify-content: space-between;
		background-color: #ffffff;
		border-radius: 8px;
		margin-right: 1px;
		/* Space between grid items */
		margin-top: 1px;
		padding: 8px;
	}

	.icon {
		width: 40px;
		/* Adjust as necessary */
		height: 40px;
		/* Adjust as necessary */
	}

	.data-info {
		display: flex;
		flex-direction: column;
		align-items: flex-end;
	}

	.data-number {
		font-size: 18px;
		/* Larger than the label */
		color: #555353;
	}

	.data-label {
		font-size: 14px;
		color: #aba7a7;
	}

	.grid {
		display: flex;
		/* 使用Flexbox布局 */
		justify-content: space-around;
		/* 栅格项之间均匀分布 */
		text-align: center;
		/* 文本居中 */
		// padding: 1px;
		/* 添加一些内边距到整个栅格容器 */
		// background-color: #f4f6fa;
		/* Light blue/white background */
		// background-color: #f4f6fa;
		/* Light blue/white background */
		// border: 1px solid #f4f6fa; /* Lighter blue border for a less stark contrast */
		// padding: 1px;
		/* Spacing inside the grid */
		// border-radius: 1px; /* Rounded corners for a softer look */
		// box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
		// margin: top 2px;
	}

	.grid-item {
		width: 150px;
		/* 每个栅格项的宽度 */
		padding: 8.5px;
		/* 栅格项内部的空间 */
		box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05);
		/* 添加一些阴影效果 */
		margin: 10px;
		/* 保持栅格项之间有一定距离 */
		border-radius: 8px;
		/* 为栅格项添加圆角 */
		background-color: #f9f9f9;
		/* 轻微的背景颜色 */
	}

	.grid-item h4 {
		// margin-bottom: 5px; /* 为标题和段落之间添加一些空间 */
		// color: #333; /* 深色文字 */
		font-size: 1rem;
		/* 标题字体大小 */
	}

	.grid-item p {
		// color: #666; /* 段落文字颜色稍微轻一些 */
		font-size: 1.2rem;
		/* 段落字体大小，稍微比标题大一些 */
		font-weight: bold;
		/* 加粗字体 */
	}

	.divider-color {
		margin-top: 0;
		background-color: #f4f6fa;
		/* 背景颜色 */
		padding: 10px;
		/* 内边距 */
		margin-top: 20px;
		/* 上边距 */
		margin-bottom: 20px;
		/* 下边距 */
	}

	.box1 {
		// border: 2px solid #3498db; /* 蓝色边框 */
		// padding: 15px; /* 内边距，使内容与边框有些距离 */
		box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
		/* 添加轻微的阴影 */
		transition: all 0.3s ease;
		/* 过渡效果，使悬浮效果更平滑 */
	}

	.box1:hover {
		border-color: #fa7f4b;
		/* 悬浮时边框颜色变为绿色 */
		box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
		/* 悬浮时阴影更明显 */
	}




	.box2 {
		// border: 3px double #9b59b6; /* 双线边框，淡紫色 */
		border-radius: 20px;
		/* 较小的圆角边框 */
		padding: 15px;
		/* 较小的内边距，使内容更靠近边框 */
		// background-color: #ffed81;
		background-color: #fdfdfd;
		/* 为元素添加背景 */
		box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
		/* 较轻微的阴影 */
		transition: all 0.3s ease;
		/* 过渡效果 */
	}

	.box2:hover {
		border-color: #f1c40f;
		/* 悬浮时边框颜色变为金色 */
		// background-color: #fef9e7; /* 悬浮时背景颜色变为淡金色 */
		// transform: translateY(-5px); /* 悬浮时轻微上移 */
		// box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2); /* 悬浮时阴影加深 */
	}

	.item {
		display: flex !important;
	}

	.child-text {
		font-size: 16px;
		/* 文本大小 */
		color: #333;
		/* 文本颜色 */
		background-color: #fffcee;
		/* 背景颜色 */
		// border: 1px solid #ddd;
		/* 边框颜色和样式 */
		border-radius: 8px;
		/* 边框圆角 */
		padding: 10px 15px;
		/* 内边距 */
		margin: 10px 0;
		/* 外边距，用于与其他元素间隔 */
		box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.1);
		/* 轻微的阴影 */

		max-width: 600px;
		/* 最大宽度，也可以是百分比 */
		margin-left: auto;
		/* 这两行用于居中 */
		margin-right: auto;
		/* 这两行用于居中 */
		display: block;
		/* 使<p>块级显示，以适用margin auto居中 */
		display: flex;
		flex-direction: column;
		justify-content: space-between;
	}

	.child-text:hover {
		transform: translateY(-5px);
		/* 悬浮时轻微上移 */
		box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
		/* 悬浮时阴影加深 */
	}

	.jzbutton {
		color: firebrick;
		background-color: lightgrey;
		transition: background-color 0.3s ease;
		/* This creates a smooth transition effect */
	}

	.jzbutton:hover {
		background-color: silver;
		/* A slightly darker shade for the hover effect */
		cursor: pointer;
		/* Changes the cursor to indicate it's clickable */
	}


	.bubble {
		background-color: #fff6d7;
		/* Light grey background */
		padding: 10px;
		/* Spacing inside the bubble */
		border-radius: 10px;
		/* Rounded corners for bubble effect */
		border-style: hidden;
		// display: inline-block; /* Allows it to wrap content */
		margin: 5px 0;
		/* Some space around the bubble */
		border: 1px solid #ccc;
		/* Optional: adds a border */
		border: none;
	}

	.red-text {

		color: #553311;
		/* Red color for the text */
		// font-weight: bold;
		/* 加粗字体 */
		border: none;
	}

	.datashow {
		border: 1px solid #d3d3d3;
		/* Light gray border */
		border-radius: 2px;
		/* Rounded corners */
		box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
		/* Subtle shadow for depth */
		background-color: white;
		/* White background */
		padding: 20px;
		/* Space inside the border */
		margin: 2px auto;
		/* Centered with space around it */
		display: block;
		/* To enable margin auto to center the box */
	}
</style>