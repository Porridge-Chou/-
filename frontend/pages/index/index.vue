
<template>
	
	<view class="index">
		<!-- <view class="search-bar-container"> -->
		<tui-list-view title="不带箭头，无点击效果" color="#777">
			<tui-list-cell :hover="false" :arrow="false" background-color="#f87e50">
		    <view class="search-bar">
		      <input type="text" ref="Input" v-model="searchKeyword" placeholder="请输入关键字" @confirm="onSearch" />
		      <button @click="onSearch">搜索</button>
		    </view>
		    <!-- 分类筛选 -->
		    <view class="filter-bar">
		      <picker mode="selector" :range="categories" @change="onCategoryChange">
		        <div class="container" style="margin-right: 150rpx;">
					<image src="../../static/img/2.png" mode="" style="height: 10px;width: 10px;margin-right: 5rpx;"></image>
		            <h4 style="color: #aa0000;font-family: 'Courier New', Courier, monospace;">{{ categories[selectedCategoryIndex] }}</h4>
		        </div>
		      </picker>
			  <picker mode="selector" :range="tags" :value="selectedTagIndex" @change="onTagChange">
			      <div class="container">
			        <image src="../../static/img/2.png" style="height: 10px;width: 10px;margin-right: 5rpx;"></image>
			        <h4 style="color: #aa0000;font-family: 'Courier New', Courier, monospace;">{{ tags[selectedTagIndex] }}</h4>
			      </div>
			    </picker>
		    </view>
		  <!-- </view> -->
		  </tui-list-cell>
	</tui-list-view>
	 <view class="container">
	 	<view class='tui-notice-board'>
	 		<view class="tui-icon-bg">
	 			<!-- <tui-icon name="news-fill" :size='24' color='#f54f46'></tui-icon>
				 -->
				 <image src="../../static/小喇叭.png" style="width: 25px;height: 25px;"></image>
	 		</view>
	 		<view class="tui-scorll-view" @tap='detail'>
	 			<view class="tui-notice" :class="[animation?'tui-animation':'']">“明光筑梦”项目已为6个地区，超过200名贫困儿童募集了捐款~</view>
	 		</view>
	 	</view>
	</view>
	<div class="colored-square"></div>
		  	<view style="margin-top: -89px;">
		  		<swiper class="card-swiper" :class="dotStyle?'square-dot':'round-dot'" :indicator-dots="true" :circular="true"
		  		 :autoplay="true" interval="5000" duration="500" @change="cardSwiper" indicator-color="#8799a3"
		  		 indicator-active-color="#0081ff">
		  			<swiper-item v-for="(item,index) in swiperList" :key="index" :class="cardCur==index?'cur':''">
		  				<view class="swiper-item">
		  					<image :src="item.url" mode="aspectFill" v-if="item.type=='image'"></image>
		  					<video :src="item.url" autoplay loop muted :show-play-btn="false" :controls="false" objectFit="cover" v-if="item.type=='video'"></video>
		  				</view>
		  			</swiper-item>
		  		</swiper>
		  	</view>
		  
		   <view class="content-grid">
		        <view 
		          class="grid-item" 
		          v-for="(item, index) in filteredItems" 
		          :key="index" 
		          v-if="itemVisible(item)"
		        >
		          <img class="item-thumbnail" :src="item.image" alt="thumbnail" style="margin-bottom: 2px;" />
				  <span
				  class="bubble"
				  v-for="(name, index) in item.tips"
				  :key="index"
				  style="line-height: 1.5;"
				  >
				  	  <span class="red-text">{{name['name']}}</span>
				  </span>
		          <view class="item-content">
				  <h4 style="align-items: center; margin: 0; padding: 0; line-height: 1.5;margin-left: 6rpx;">{{ item.name}}&nbsp;&nbsp;&nbsp;{{item.age}}岁</h4>
				  <div style="margin: 0; padding: 0; line-height: 1.5;color: dimgrey;margin-left: 6rpx;">{{item.category}}</div>
				  </view>
		          <!-- 其他标签等。。 -->
				  <tui-button type="warning" shape="circle" height="65rpx" size="60rpx" @click="goToDetails(item)">查看详情</tui-button>
				</view>
				<view v-if="filteredItems==''">
				<view class="empty-tips">
					 <tui-no-data imgUrl="/static/images/toast/img_noorder_3x.png"  btnText="重新搜索" @click="focusInput">
					   <text class="tui-color__black">暂无数据~</text>
					   <!--如果需要自定义按钮，可在插槽中自定义，不使用默认按钮-->
					 </tui-no-data>
				</view>
				</view>
		    </view>
			<!-- <tui-scroll-top :scrollTop="scrollTop" topIcon="/static/img/回到顶部.png"></tui-scroll-top> -->
			<tui-toast ref="toast"></tui-toast>
			<tui-scroll-top :scrollTop="scrollTop" topIcon="/static/img/回到顶部.png"></tui-scroll-top>
		    </view>
</template>

<script>
	import tuiFooter from "@/components/thorui/tui-footer/tui-footer.vue"
	import tuiToast from "@/components/thorui/tui-toast/tui-toast.vue"
	import tuiNoData from "@/components/thorui/tui-no-data/tui-no-data.vue"
	import tuiButton from "@/components/thorui/tui-button/tui-button.vue"
	import tuiTag from "@/components/thorui/tui-tag/tui-tag.vue"
	import tuiListCell from "@/components/thorui/tui-list-cell/tui-list-cell.vue"
	import tuiDropdownList from "@/components/thorui/tui-dropdown-list/tui-dropdown-list.vue"
	import tuiScrollTop from "@/components/thorui/tui-scroll-top/tui-scroll-top.vue"	
		export default {
			cardSwiper(e) {
			    this.cardCur = e.detail.current
			},
			components:{
				tuiButton,
				tuiTag,
				tuiListCell,
				tuiDropdownList,
				tuiScrollTop,
				tuiNoData,
				tuiToast,
				tuiFooter
			},
		watch: {
		    searchKeyword(newVal) {
		      // 关键词变为空字符串，自动刷新
		      if (newVal === '') {
		        this.onSearch();
		      }
		    }
		  },
		data() {
			return {
				animation: false,
				scrollTop: 0,
				searchKeyword: '', // 关键词
				categories: ['地区', '西藏', '辽宁', '甘肃','云南' ,'广西', '贵州'],//地区范围
				selectedCategoryIndex: 0, //地区索引显示
				items: [],
				filteredItems: [],
				item: {
					id:'',
					image:'',
					name:'',
					category:'',
					tips:[]
				},
				ChildList:[],
				tags: ['标签', '父母双亡' ,'家庭贫困' ,'留守儿童', '身有残疾', '心脏病', '智力障碍'], // 标签
				selectedTagIndex: 0 ,// 默认选中“全部”
				swiperList: [{
					id: 0,
					type: 'image',
					url: '../../static/img/swiper1-4.png'
				}, {
					id: 1,
					type: 'image',
					url: '../../static/img/swiper2-2.png',
				}, {
					id: 2,
					type: 'image',
					url: '../../static/img/swiper3-3.png'
				}, {
					id: 3,
					type: 'image',
					url: '../../static/img/swiper4-2.jpg'
				}],
				dotStyle: true
			}
		},
		mounted() {
		    // 组件挂载后立即调用搜索，以初始化视图
			console.log(this.item)
			this.fetchData()
		  },
		onLoad: function(options) {
		 		setTimeout(() => {
		 			this.animation = true
		 		}, 600)
		 	},
		methods: {
			detail(e) {
			 			console.log('详情~')
			 		},
			showToast: function(e) {
			  		let params = {
			  			title: "刷新成功",
			  			imgUrl: "/static/img/964.png",
			  			icon: true
			  		}
			  		this.$refs.toast && this.$refs.toast.show(params);
			  	},
			focusInput() {	  uni.navigateBack() },
			goToDetails(item) {
			      const itemData = encodeURIComponent(JSON.stringify(item));
			      uni.navigateTo({
			        url: '/pages/ItemDetails/ItemDetails?data=' + itemData
			      });
			    },
				
			async fetchData(){
				const result = await uni.request({
					url:"http://localhost:8085/child/getChildList",
					method:'GET',
					header: {
						'Content-Type':'application/json',
					}
				})
				// const swiperresult = await uni.request({
				// 	url:"http://localhost:8080/child/findSwiper",
				// 	method:'GET',
				// 	header: {
				// 		'Content-Type':'application/json',
				// 	}
				// })
				//根据查表组建视图
				if(result.statusCode==200){
					var responseData=result.data;
					console.log(responseData)
					for (let i = 0; i < responseData.message.length; i++) {
					        let item = {
					          id: responseData.message[i].id,
					          name: responseData.message[i].name,
					          age: responseData.message[i].age,
					          category: responseData.message[i].address,
					          image: responseData.message[i].headPhoto,
					          tips: JSON.parse(responseData.message[i].tag),
					        };
					        this.items.push(item);
					      }
					console.log(this.items)
				}else{
					console.log('ERROR')
				}
				this.onSearch()
			},
			onSearch() {
			  // 根据关键字进行搜索
			  const keywordFilteredItems = this.items.filter((item) => {
			    const keywordMatch = item.name.toLowerCase().includes(this.searchKeyword.toLowerCase());
			    const categoryMatch = this.categories[this.selectedCategoryIndex] === '地区' || item.category.toLowerCase().includes(this.categories[this.selectedCategoryIndex].toLowerCase());
			    return keywordMatch && categoryMatch;
			  });
			  // 进一步根据标签筛选
			  this.filteredItems = keywordFilteredItems.filter((item) => {
			    const tagMatch = this.tags[this.selectedTagIndex] === '标签' || item.tips.some(tip => tip.name === this.tags[this.selectedTagIndex]);
			    return tagMatch;
			  });
			  this.showToast()
			},
				      itemVisible(item) {
				        //你可以进一步筛选哪些项目是可见的
				        return true; // 这里默认所有筛选出的项目都是可见的
				      },
					  onCategoryChange(event) {
						  
					        // 当分类改变时，更新 selectedCategoryIndex
					        this.selectedCategoryIndex = event.detail.value;
					        // 也可以在这里直接进行搜索
					        this.onSearch();
					      },
						  onTagChange(event) {
						      this.selectedTagIndex = event.detail.value;
						      // 触发搜索以应用新的筛选器
						      this.onSearch();
						    }
					 
					  },
					onPageScroll(e) {
					 	this.scrollTop = e.scrollTop;
					 }
		}
</script>

<style lang="scss">
	@keyframes gradientBackground {
	  0% {
	    background-position: 0% 50%;
	  }
	  50% {
	    background-position: 100% 50%;
	  }
	  100% {
	    background-position: 0% 50%;
	  }
	}
	
	body {
	  margin: 0;
	  padding: 0;
	  height: 100vh;
	  animation: gradientBackground 15s ease infinite;
	  background: linear-gradient(-45deg, #ffffff, #ffffff, #ffffff, #ffffff);
	  background-size: 400% 400%;
	  -webkit-font-smoothing: antialiased;
	}

	.search-bar-container {
	  display: flex;
	  flex-direction: column;
	  padding: 20px;
	  background-color: #e7e7e7; /* Light green background for visibility */
	  border-radius: 8px; /* Rounded corners for a smoother look */
	  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* Subtle shadow for a floating effect */
	  transition: box-shadow 0.3s ease; /* Smooth transition for the shadow */
	  margin: 10px; /* Ensure some space around the container */
	}
	
	
	
	/* Slightly raise the search bar container on hover to create a floating effect */
	.search-bar-container:hover {
	  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.3);
	}
	
	.search-bar {
	  display: flex;
	  border: 1px solid orange; /* 搜索框的边框 */
	  border-radius: 4px; /* 搜索框的圆角 */
	  overflow: hidden; /* 防止子元素溢出 */
	  height: 32px;
	}
	
	.search-bar input {
	  flex: 1;
	  padding: 8px; /* 输入框内边距 */
	  border: none; /* 去掉输入框的边框 */
	  outline: none; /* 去掉点击输入框时的边框 */
	}
	
	.search-bar button {
	  padding: 0.2px 10px; /* 按钮内边距 */
	  border: none; /* 去掉按钮的边框 */
	  background-color: #3ce8e3; /* 按钮背景颜色 */
	  color: white; /* 按钮文字颜色 */
	  cursor: pointer; /* 鼠标悬停时显示指针 */
	  border-radius: 0 8px 0.1px 0.2; /* 右侧圆角 */
	  
	  /* 使用 flex 布局来实现垂直居中对齐 */
	  display: flex;
	  align-items: center; /* 垂直居中对齐文本 */
	}

	
	.search-bar button:hover {
	  background-color: #1669c1; /* 按钮悬停时的背景色 */
	}
	.search-bar {
		// z-index: 999;
	  display: flex;
	  align-items: center; /* Centers the input and button vertically */
	  background-color: #ffffff; /* White background */
	  // padding: 3px; /* Minimal padding to keep elements snug */
	  border-radius: 30px; /* Rounded borders for the search bar */
	  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1); /* Soft shadow for depth */
	  max-width: 500px; /* Maximum width */
	  margin: 10px auto; /* Margin for auto centering */
	}
	.colored-square {
	  width: 100%; /* 设置方块宽度，根据需要调整 */
	  height: 100px; /* 设置方块高度，根据需要调整 */
	  background-color: #f87e50; /* 设置背景颜色，可以根据需要更改 */
	  border-bottom-left-radius: 40px; /* 左下角圆角半径，根据需要调整 */
	  border-bottom-right-radius: 40px; /* 右下角圆角半径，根据需要调整 */
	}
	.search-bar input[type="text"] {
	  border: none;
	  outline: none;
	  flex-grow: 1; /* Input will grow to take up all available space */
	  margin-right: -1px; /* Helps remove any gap between input and button */
	}
	
	.search-bar button {
	  padding: 3px 8px; /* Adjust padding inside the button */
	  border: none;
	  border-radius: 20px; /* Rounded borders for the button */
	  background-color: #ffaa7f; /* Bootstrap primary color for example */
	  color: white;
	  cursor: pointer; /* Pointer cursor on hover */
	  outline: none;
	  transition: background-color 0.3s ease; /* Transition for button hover */
	  /* Remove margin if not needed, adjust if it is */
	}

	
	/* Rest of your styles remain the same... */

	
	.search-bar button:hover {
	  background-color: #ff5500; /* Darker blue on hover */
	}
	
	/* Reduced floating effect */
	.search-bar:hover {
	  transform: translateY(3px);
	}
	
	.empty-tips {
	  display: flex;
	  justify-content: center; /* 水平居中对齐 */
	  align-items: center; /* 垂直居中对齐 */
	  height: 100vh; /* 使内容填充整个视口高度 */
	}
	
	.styled-text {
	  font-size: 24px; /* 设置字体大小 */
	  color: #ffaa00; /* 设置字体颜色 */
	  font-weight: bold; /* 设置字体粗细 */
	  text-align: center; /* 居中对齐文本 */
	  /* 你可以添加其他样式，如字体样式、边距、背景颜色等 */
	}
	
	.filter-bar {
	  display: flex;
	  justify-content: center; /* This will center the items horizontally */
	  align-items: center;
	}
	
	.picker-container {
	  display: flex;
	  align-items: center;
	  /* Define a horizontal margin for spacing between the pickers */
	  margin: 0 8px; /* Adjust this value as needed for your design */
	}
	
	/* Remove the horizontal margin for the first and last picker for even spacing */
	.picker-container:first-child,
	.picker-container:last-child {
	  margin-right: 0;
	  margin-left: 0;
	}



	/* 设置swiper容器的样式 */
	.custom-swiper {
	  width: 100%; // Takes the full width of the container
	  border-radius: 10px; // Rounded corners for a softer look
	  overflow: hidden; // Prevents images from spilling out of the swiper's boundaries
	  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); // Subtle shadow for depth
	
	  .swiper-pagination-bullet-active {
	    background-color: #ffffff; // Active pagination dot color
	  }
	
	  .swiper-pagination-bullet {
	    background-color: rgba(255, 255, 255, 0.5); // Inactive pagination dot color
	    width: 8px; // Size of the dot
	    height: 8px; // Size of the dot
	    border-radius: 50%; // Makes the dots circular
	    margin: 0 4px; // Space between dots
	  }
	}
	
	.swiper-image {
	  width: 100%; // Ensures the image takes the full width of the swiper item
	  height: auto; // Maintains the aspect ratio of the image
	  object-fit: cover; // Covers the swiper item area, could be changed to "contain" based on preference
	}


	.content-grid {
	  border: 1px solid #cccccc;
	  border-radius: 5px;
	  display: flex;
	  flex-wrap: wrap;
	  padding: 10px;
	  justify-content: flex-start;
	  background-color: #ffffff;
	}


	
	.grid-item {
	  width: calc(50% - 10px); /* Subtract the total margin from the 50% width */
	  background-color: #ffffff;
	  margin: 5px; /* Half the total horizontal space you want between items */
	  padding: 10px;
	  box-sizing: border-box; /* Include padding and border in the element's total width and height */
	  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
	  border-radius: 8px;
	  transition: transform 0.3s, box-shadow 0.3s;
	  margin-bottom: 2px;
	}

	
	.grid-item:hover{
		transform: translateY(-5px); /* 悬浮时轻微上移 */
		box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2); /* 悬浮时阴影加深 */
	}
	
	.item-thumbnail {
	  display: block; /* Makes the container a block element, which can hold an image */
	  width: 100%; /* Ensures that the container stretches across the width of its parent */
	  height: 150px; /* Fixes the height to ensure uniformity */
	  overflow: hidden; /* Hides any part of the image that overflows */
	}
	
	.item-thumbnail img {
	  width: 100%; /* Stretches the image across the width of its container */
	  height: auto; /* Adjusts the height automatically to maintain the aspect ratio */
	  object-fit: cover; /* Ensures the image covers the area, cropping if necessary */
      margin-bottom: 3px;
	}

	.item-content {
	  font-size: 16px;
	  color: #333;
	    // font-weight: bold; /* Add this line to make the text bold */
	}
	
	.mess_button {
	  padding: 1px 5px; /* Reduce padding for a smaller button */
	  background-color: #ff007f;
	  color: white;
	  border: none;
	  border-radius: 4px;
	  cursor: pointer;
	  transition: background-color 0.2s;
	}
	
	.mess_button:hover {
	  background-color: #ffaaff;
	}
	
	.bubble {
	  background-color: #f3a34c;
	  border: 1.2px solid #f3a34c;
	  border-radius: 20px;
	  padding: 2.2rpx;
	  // font-weight: bold;
	  margin-left: 20px;
	  margin: 1.5px;
	}
	
	.container {
	  display: flex;
	  align-items: center; /* 垂直居中对齐 */
	}
	
	.red-text {
	  color: #ffffff; /* Red color for the text */
	}
	
	/* 头部 轮播图 */
	.carousel-section {
		position: relative;
		padding-top: 10px;
	
		.titleNview-placing {
			height: var(--status-bar-height);
			padding-top: 44px;
			box-sizing: content-box;
		}
	
		.titleNview-background {
			position: absolute;
			top: 0;
			left: 0;
			width: 100%;
			height: 426upx;
			transition: .4s;
		}
	}
	// .scroll{
	// 	box-sizing: border-box;
	// 	height: 300rpx;
	// 	.imggroup{
	// 		white-space: nowrap;
	// 		.imgitem{
	// 			width: 750rpx;
	// 			height: 300rpx;
	// 			background-color: #8f8f94;
	// 			display: inline-block;
	// 		}
	// 	}
	// }
	
	 .container {
	 	padding-top: 0rpx;
	 }
	
	 .tui-notice-board {
	 	width: 100%;
	 	padding-right: 30rpx;
	 	box-sizing: border-box;
	 	font-size: 28rpx;
	 	height: 60rpx;
	 	background: #fff8d5;
	 	display: flex;
	 	align-items: center;
	 	position: absolute;
	 	top: 0;
	 	/* #ifdef H5 */
	 	top: 80px;
	 	/* #endif */
	 	z-index: 999;
	 }
	
	 .tui-icon-bg {
	 	background: #fff8d5;
	 	padding-left: 30rpx;
	 	position: relative;
	 	z-index: 10;
		// background-image: "../../static/img/小喇叭.png";
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

