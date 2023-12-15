<template>
  <view class="container" v-if="item">
    <!-- 头部logo -->
    <view class="logo-container">
      <image class="logo" src="../../static/img/org2.png"></image>
    </view>
    <!-- 组织名称 -->
    <view class="org-name">{{item.name}}</view>
    <!-- 组织信息 -->
    
	<view class="tui-card-class tui-card" :class="[full?'tui-card-full':'',border?'tui-card-border':'']"
		@tap="handleClick" @longtap="longTap">
		
		<view class="tui-card-body">
			<slot name="body">
				<view class="contact-info">
				  <text>电话: {{item.phone}}</text>
				  <text>邮箱: charity@example.com</text>
				</view>
				<!-- 地址 -->
				<view class="address">
				  <text>地址: {{item.address}}</text>
				</view>
				<!-- 简介 -->
				<view class="intro">
				  <text>简介：{{item.description}}</text>
				</view>
				<!-- 负责人 -->
				<view class="director">
				  <text>负责人: {{item.manager}}</text>
				</view>
			</slot>
		</view>
		
	</view>
	<view class="tui-card-class vtui-card" :class="[full?'tui-card-full':'',border?'tui-card-border':'']"
		@tap="handleClick" @longtap="longTap">
		
		<view class="tui-card-body">
			<slot name="body">
				<view class="volunteers">
				  <text class="volunteers-title">志愿者列表：</text>
				  <view class="volunteer" >
				    <text>{{item.volunteerList}}</text>
				  </view>
				</view>
			</slot>
		</view>
		
	</view>
	
	<view class="tui-card-class ctui-card" :class="[full?'tui-card-full':'',border?'tui-card-border':'']"
		@tap="handleClick" @longtap="longTap">
		
		<view class="tui-card-body">
			<slot name="body">
			  <view class="certificates">
			    <text class="certificates-title">认证证书：</text>
				<br />
			    <!-- 新添加的证书图片 -->
			    <image class="certificate-inline" src="/static/img/证书.jpg" mode="aspectFit"></image>
			  </view>	
			</slot>
		</view>
		
	</view>
	
  </view>
</template>

<script>
	import tuiButton from "@/components/thorui/tui-button/tui-button.vue"
		export default {
			components:{
				tuiButton
			},
		data() {
			return {
				item: null
			};
		},
		onLoad(options) {
		const b = options.id
		 console.log(b,'=-->ssssssssssssssss')
		 this.dataList(b)
		},
		methods:{
			async dataList(v){
				console.log(v,'------------aa');
				try{
					const result = await uni.request({
						url:`http://localhost:8085/organization/Odetail?id=${v}`,
						method:'GET',
						header: {
							'Content-Type':'application/json',
						}
					})
					console.log(result,'======》');
					const { code,message } = result.data
					this.item = message
 					// console.log(message,'--------asd');
				}catch(err){
					console.log(err);
				}
			},
			
			
		}
		

	}
</script>

<style lang="scss">
 
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 10px;
}

.logo-container {
  margin-bottom: 10px;
}

.logo {
  width: 100px;
  height: 100px;
}

.org-name {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20rpx;
}

.org-info {
  display: flex;
  flex-direction: column;
}

.contact-info text,
.address text,
.intro text,
.director text {
	padding-left: 75rpx;
  display: block; /* Makes each text element occupy its own line */
  margin-top: 20rpx;
  margin-bottom: 10px; /* Adds space between each text element */
  font-weight: bold;
}

.volunteers{
  
  margin-bottom: 10px;
}

.volunteers-title{
	margin-top: 20rpx;
  margin-bottom: 20px;
  padding-left: 75rpx;
  font-weight: bold;
  
}

.volunteer {
  padding-left: 75rpx;
  background-color: #ffffff;
  margin-top: 10px;
  font-weight: bold;
}

.certificates {
  display: flex;
  align-items: center; // 保证文字和图片垂直居中对齐
  margin-bottom: 10px;
}

.certificates-title {
	padding-left: 75rpx;
  font-weight: bold;
}

.certificate-inline {
  width: 160px; // 你可以根据实际需要调整图片的宽度
  height: 160px;
  
   padding-top: 20rpx;// 添加一点空间在文字和图片之间
   padding-left: 20rpx;
}
.tui-card {
	    width: 90%;
		height: 350rpx;
		margin-bottom: 30rpx;
		font-size: 25rpx;
		background-color: #fff;
		border-radius: 30rpx;
		box-shadow: 0 0 30rpx #dddddd;
		box-sizing: border-box;
		overflow: hidden;
	}
	

	.tui-card-body {
		font-size: 32rpx;
		color: #262b3a;
		box-sizing: border-box;
	}
	
.vtui-card {
	width: 90%;
	height: 150rpx;
		margin-top: 5px;
		font-size: 28rpx;
		background-color: #fff;
		border-radius: 30rpx;
		box-shadow: 0 0 30rpx #dddddd;
		box-sizing: border-box;
		overflow: hidden;
	}

	

	.tui-card-body {
		font-size: 32rpx;
		color: #262b3a;
		box-sizing: border-box;
	}
		
.ctui-card {
	width: 90%;
	height: 300rpx;
		margin-top: 20px;
		font-size: 28rpx;
		background-color: #fff;
		border-radius: 30rpx;
		box-shadow: 0 0 30rpx #dddddd;
		box-sizing: border-box;
		overflow: hidden;
	}

	

	.tui-card-body {
		font-size: 32rpx;
		color: #262b3a;
		box-sizing: border-box;
	}			
</style>
