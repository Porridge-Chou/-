<template>
	
	<view class="container1">
		
	 	<view class='tui-notice-board'>
	 		<view class="tui-icon-bg">
	 			<!-- <tui-icon name="news-fill" :size='24' color='#f54f46'></tui-icon> -->
				<image src="../../static/小喇叭.png" style="margin-top: 5px;width: 45rpx;height: 45rpx;" mode=""></image>
	 		</view>
	 		<view class="tui-scorll-view" @tap='detail'>
	 			<view class="tui-notice" :class="[animation?'tui-animation':'']">高晓梅 在 5 分钟前，捐助了 200 元；&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				李娟 在 3 分钟前，捐助了 150 元；&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				赵宇 在 2 分钟前，捐助了 180 元；&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				张华 在 1 分钟前，捐助了 200 元</view>
	 		</view>
	 	</view>
	</view>
	
	<view class="container">
		<view class="content">
		
			<view style="font-size: 50rpx; font-family: STXingkai ;font-size: 40px;color: brown">捐助方式</view>
						<text style="color: brown;">___________________</text>
			<view class="uniformitem" style="display: flex;">
				<view class="title" >
					<image class="money" src="../../static/img/钱袋.png" mode="heightFix"></image>
				</view>
				<input  v-model="countNumber" placeholder="输入捐款数额(元)" style="width:400rpx;"/>
			</view>
				
			
			<view class="uniformitem2" style="display: flex;">
				<view class="title" >
					<image class="thing" src="../../static/img/物品.png" mode="heightFix"></image>
				</view>
					<view class="uni-list">
						<view class="unilistcell" style="height: 15rpx; width:360rpx;">
							<view class="nowselect">
								请选择
							</view>
							<view class="unipicker">
								<picker @change="bindPickerChange" :value="index" :range="array">
									<view class="selectinput">{{array[index]}}</view>
								</picker>
							</view>
						</view>
					</view>
			</view>	  
				  
			 
			<button class="donate" @click="submitform()">确认捐助</button>
		</view>
	</view>
</template>

<script>
	let childid;
	export default {
		
		// 在新页面中的onLoad生命周期函数中接收参数
		onLoad: function (options) {
		  // 获取传递过来的childId
		  childid = options.childId;
		  console.log("Received childId: " + childid);
		  
		  // 在这里进行后续操作
		  setTimeout(() => {
		  	this.animation = true
		  }, 600)
		},
		// onLoad: function(options) {
		 		
		//  	},
		data() {
			return {
				animation: false,
				countNumber: '',
				array: ['字典','书包', '故事书','科普书','计算器','芭比娃娃',
					'练习本', '铅笔', '棉袄', '棉鞋','课本',
				],
				index :0,
				selectedOption: '',
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
			}
		},
		methods: {
			detail(e) {
			 			console.log('详情~')
			 		},
			bindPickerChange: function(e) {
			    console.log('picker发送选择改变，携带值为', e.detail.value)
			    this.index = e.detail.value
			},
			submitform() {
							
							const date = new Date();
							const formattedTime = `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')} ${date.getHours().toString().padStart(2, '0')}:${date.getMinutes().toString().padStart(2, '0')}:${date.getSeconds().toString().padStart(2, '0')}`;
							console.log(formattedTime);
							
							this.children.data = formattedTime
							
							console.log('发出的捐款'+this.money)
							
							this.children.money = this.countNumber;
							
							const uid = uni.getStorageSync('id');
							
							// console.log('zhelishinihao'+childid)
							// console.log(uid);
							// console.log(this.array[this.index])
							
							const donate = {
								id : String(uid),
								
							};
							const child = {
								
								id : String(childid),
									
							};
							
							
							// const formData = {
							// 	donate: uid,
							// 	child : childid,
							// 	money: this.countNumber
								
							// 	// type: this.array[this.index]
							// };
							// console.log(this.countNumber);
							// console.log(formData)
							
							if (!this.countNumber) {
								uni.showToast({
									title:'金额不能为空',
									icon : 'none',
									duration:2000
								})
								return;
						    }
						  
							uni.request({
								url: 'http://localhost:8085/donate/writeDonateRecord', 
								method: 'POST',
								header: {
									'Content-Type': 'application/json' // 设置请求头部为JSON格式
								},
								data: {
									donate: donate,
									child : child,
									money : Number(this.countNumber),
									item : String(this.array[this.index]) 
								},
								
								
								success: (res) => {
									// console.log('juanzhuren'+donate)
									// console.log('haizi'+child)
									// console.log('qian'+this.countNumber)
									// console.log(res);
									uni.showToast({
										title:'捐助成功',
										icon : 'none',
										duration:2000
									})
									
									
									const donateData = encodeURIComponent(JSON.stringify(this.children));
									uni.navigateTo({
									  url: '/pages/certificate/certificate?data=' + donateData
									});
									
									
									
								},
								fail: (err) => {
								  console.error(err);
								  // 处理失败情况
								}
						  });
						}

		}
	}
</script>

<style>

	.container {
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		background-color: #f5f5f5;
		display: flex;
		justify-content: center;
		align-items: center;
	}
	
	.content{
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		height: 60%;
		width: 95%;
		background-color: #ffffff;
		border-radius: 30px; /* 设置圆角半径 */
	}
	
	.thing{
		height: 45rpx;
	}
	
	.money{
		height: 45rpx;
	}
	
	.uniformitem {
		border-radius: 30rpx;
		padding: 20rpx;
		margin-top: 70rpx;
		background-color: #F7F4F2;

		
	}
	.uniformitem2 {
		background-color: #fff;
		border-radius: 30rpx;
		padding: 20rpx;
		margin-top: 60rpx;
		background-color: #F7F4F2;
	}
	
	.donateinput {
	  width: 60%;
	  height: 25px;
	  border: 1px solid #ccc;
	  border-radius: 4px;
	  padding: 5px 10px;
	}
	
	.unititle {
	  font-size: 18px;
	  margin-bottom: 10px;
	}
	
	.uni-list {
	  background-color: #f5f5f5;
	  padding: 10px;
	  border-radius: 4px;
	}
	
	.unilistcell {
	  display: flex;
	  align-items: center;
	  
	}
	
	.nowselect {
	  font-size: 16px;
	  flex: 1;
	}
	
	.unipicker {
		width: 60rpx;
		flex: 2;
		
	}
	
	.selectinput {
	  border: 1px solid #ccc;
	  border-radius: 4px;
	  padding: 5px 10px;
	}
	
	.donate {
		
		margin-top: 60rpx;
		background: linear-gradient(to bottom, #FF8653,  #FAA94B);
		color: #fff;
		padding: 5px 20px;
		font-size: 15px;
		cursor: pointer;
		height: 50px;
		width: 125px;
		border-radius: 50rpx;
	}	

	page{
		background-color: #ffffff;
	}
	
	
	 .container1 {
	 	padding-top: 120rpx;
	 }
	
	 .tui-notice-board {
	 	width: 100%;
	 	padding-right: 30rpx;
	 	box-sizing: border-box;
	 	font-size: 28rpx;
	 	height: 110rpx;
	 	background: #fff8d5;
	 	display: flex;
	 	align-items: center;
	 	position: fixed;
	 	top: 0;
	 	/* #ifdef H5 */
	 	top: 74px;
	 	/* #endif */
	 	z-index: 999;
	 }
	
	 .tui-icon-bg {
	 	background: #fff8d5;
	 	padding-left: 30rpx;
	 	position: relative;
	 	z-index: 10;
	 }
	
	 .tui-icon-class {
	 	margin-right: 12rpx;
	 }
	
	 .tui-scorll-view {
	 	flex: 1;
	 	line-height: 1;
	 	white-space: nowrap;
	 	overflow: hidden;
	 	color: #f54f46;
	 }
	
	 .tui-notice {
	 	-webkit-backface-visibility: hidden;
	 	-webkit-perspective: 1000;
	 	transform: translate3d(100%, 0, 0);
	 }
	
	 .tui-animation {
	 	-webkit-animation: tui-rolling 12s linear infinite;
	 	animation: tui-rolling 12s linear infinite;
	 }
	
	 @-webkit-keyframes tui-rolling {
	 	0% {
	 		transform: translate3d(100%, 0, 0);
	 	}
	
	 	100% {
	 		transform: translate3d(-170%, 0, 0);
	 	}
	 }
	
	 @keyframes tui-rolling {
	 	0% {
	 		transform: translate3d(100%, 0, 0);
	 	}
	
	 	100% {
	 		transform: translate3d(-170%, 0, 0);
	 	}
	 }
	
	 .tui-subject {
	 	padding: 100rpx 30rpx 30rpx 30rpx;
	 	box-sizing: border-box;
	 	font-size: 32rpx;
	 	font-weight: bold;
	 }
	
	 .tui-rolling-news {
	 	width: 100%;
	 	padding: 12rpx 30rpx;
	 	box-sizing: border-box;
	 	display: flex;
	 	align-items: center;
	 	justify-content: center;
	 	flex-wrap: nowrap;
	 }
	
	 .tui-swiper {
	 	font-size: 28rpx;
	 	height: 50rpx;
	 	flex: 1;
	 }
	
	 .tui-swiper-item {
	 	display: flex;
	 	align-items: center
	 }
	
	 .tui-news-item {
	 	line-height: 28rpx;
	 	white-space: nowrap;
	 	overflow: hidden;
	 	text-overflow: ellipsis;
	 }
	
	 .tui-searchbox {
	 	padding: 60rpx 80rpx;
	 	box-sizing: border-box;
	 }
	
	 .tui-rolling-search {
	 	width: 100%;
	 	height: 70rpx;
	 	border-radius: 35rpx;
	 	padding: 0 40rpx 0 30rpx;
	 	box-sizing: border-box;
	 	background: #fff;
	 	display: flex;
	 	align-items: center;
	 	justify-content: center;
	 	flex-wrap: nowrap;
	 	color: #999;
	 }
</style>
