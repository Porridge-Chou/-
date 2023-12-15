<template>
  <view class="page" v-if="item">
	  
	 <view class="box" >
		<view class="icontainer" v-if="item" >
			<view class="ninfo-item" >	
		 	  <text class="info-content" 
			  style="font-family: STXingkai;font-weight: bold;font-size: 50px;color: rgb(200, 50, 10);">
			  {{item.name}}</text>
		 	</view>
		 	<view class="ainfo-item" style="margin-top: -19px;">
		 	  <text class="info-age"
			  style="font-family: STKaiti;font-weight: bold;font-size: 30px;color: gold;
						margin-left: 47px;"
			  >{{item.age}}岁</text>
		 	</view>
			<view class="sinfo-item" style="margin-top: -10px;">
			  <text class="info-school"
			  style="font-family: STKaiti;font-weight: bold;font-size: 25px;color: gold;
			  						margin-left: 10px;">
									{{item.school}}</text>
			</view>
			<view>
				<!-- <image src="/static/img/王小明.jpg" mode="" class="timage"></image> -->
				<image :src="head_photo" mode="" class="timage"></image>
			</view>
		 </view> 
		<!-- <view class="pcontainer" style="width: 2px;height:2px"> -->
		<!-- <view class="pcontainer"> -->
		
		<!-- </view> -->
		 
	 </view> 
	
	 <view class="card-container" :style="{ width: cardWidth + 'px', transform: 'translate(' + posX + 'px,' + posY + 'px)' }">
	    <view class="info-block" v-if="item">
	     
		 <view class="info-item">
		   <text class="info-title">地址：</text>
		   <text class="info-content">{{item.address}}</text>
		 </view>
		 <view class="info-item">
		   <text class="info-title">年级：</text>
		   <text class="info-content">{{item.grade}}年级</text>
		 </view>
		 <view class="info-item">
		   <text class="info-title">性别：</text>
		   <text class="info-content">{{item.gender}}</text>
		 </view>
		 <view class="info-item">
		   <text class="info-title">简介：</text>
		   <text class="info-content">{{item.description}}</text>
		 </view>
		  <view class="tags">
		     <text v-for="(tag, index) in changeTag(item.tag)" :key="index" class="tag">
		 			  {{tag.name}}
		 		  </text>
		   </view>
		 
		       <!-- 对接志愿者信息 -->
		       <view class="info-item">
		         <text class="info-title">对接志愿者：</text>
		         <text class="info-content">
		           {{item.volunteerName}}
		           <!-- 聊天按钮紧随志愿者名称后面 -->
		           <navigator :url="`/pages/chat/chat?fromId=${this.donaterId}&toId=${item.volunteerId}`" class="chat-link" style="margin-top: -24px;">
		             聊天
		           </navigator>
		         </text>
		       </view>
		 
	      <view class="info-item">
	        <text class="info-title">所属组织：</text>
	        <text class="info-content">
	          {{item.organizationName}}
	          <navigator :url="`/pages/organizationInfo/organizationInfo?id=${item.organizationId}`" class="details-link" style="margin-top: -23px;">
	            详情
	          </navigator>
	        </text>
	      </view>
	    </view>
	  </view>
	
			<view class="button-container">
			    <navigator url="/pages/donate/donate" class="button donate" open-type="navigate" style="display: flex;margin-top: -15rpx;">
					<image src="../../static/img/按钮.png" mode="" class="image-class"></image>
					<div>我要捐助</div>
				</navigator>
			    
			  </view>
           
		    <view class="gbutton-container">
		        <view class="button-guanzhu" @click="follow()" style="margin-top: -44.5px;">
		            <view :class="['heart', isFollowed ? 'followed' : '']"  style="display: flex;">
						<image src="../../static/img/我要关注.png" mode="" class="image-guanzhu"></image>
						<text class="follow-text">{{ isFollowed ? '已关注' : '我要关注' }}</text>
		        	</view>

		        </view>
		    		
		      </view>
		   
		   
    </view>
	<view class="grow">
		
			<tui-divider dividerColor="red" width="80%" style="border-radius: 20px 20px 0 0 ;"><h3 style="color: #f87e50;">任务完成情况</h3></tui-divider>
					<tui-list-view title="数据统览" color="#777">
						<tui-list-cell :hover="false" :arrow="false">
							
								<div class="container" style="display: flex; justify-content: space-between;align-items: center;">
								  <div class="task" style="flex-grow: 1; flex-shrink: 1;">
								    <div class="grid-container">
								      <img src="/static/img/当日.png" style="width: 120rpx; height: 120rpx;" mode="" />
								      <br />
								      <h4 style="font-weight: bold;margin-left: 2px;">必做任务</h4>
								    </div>
								    <h3 style="margin-left: 10px;">{{musttask_f}}/{{musttask_t}}</h3>
								  </div>
								
								  <div class="task" style="flex-grow: 1; flex-shrink: 1;">
								    <div class="grid-container">
								      <img src="/static/img/任务 1.png" style="width: 120rpx; height: 120rpx;" mode="" />
								      <br />
								      <h4 style="font-weight: bold;margin-left: 2px;">选做任务</h4>
								    </div>
								    <h3 style="margin-left: 15px;">{{opttask_f}}/{{opttask_t}}</h3>
								  </div>
								
								  <div class="point" style="flex-grow: 1; flex-shrink: 1;">
								    <div class="grid-container">
								      <img src="/static/img/points.png" style="width: 120rpx; height: 120rpx;" mode="" />
								      <br />
								      <h4 style="font-weight: bold;margin-left: 3px;">积分总数</h4>
								    </div>
								    <h3 style="margin-left: 17px;">{{point}}</h3>
								  </div>
								</div>



							
						</tui-list-cell>
					</tui-list-view>
					<tui-list-view title="进度" color="#777">
						<tui-list-cell :hover="false" :arrow="false">
							<view class="process">
								<div>
									  <h4>必做进度：</h4>
									 <div class="progress-container" style="padding: 20rpx;">
									       <div class="static-progress">
									         <div class="static-progress1-fill" style="border-radius: 5px; display: flex; align-items: center; justify-content: center;" v-if="process1" :style="{width: process1 + '%'}">
									           <span style="font-size: 3px; color: white;font-weight: bold;">{{process1}}%</span>
									         </div>
									       </div>
									     </div>
								</div>
								<tui-divider style="height:27rpx;"></tui-divider>
								<div>
									<h4>选作进度：</h4>
									<div class="progress-container" style="padding: 20rpx;">
														       <div class="static-progress">
														         <div class="static-progress2-fill" style="border-radius: 5px; display: flex; align-items: center; justify-content: center;" v-if="process1" :style="{width: process2 + '%'}">
														           <span style="font-size: 3px; color: white;font-weight: bold;">{{process2}}%</span>
														         </div>
														       </div>
														     </div>
								</div>
							</view>
						</tui-list-cell>
					</tui-list-view>
					<view>
							<tui-divider dividerColor="red" width="80%" ><h3 style="color: #f87e50;">近期钱款去向</h3></tui-divider>
							<tui-list-view title="钱款去向" color="#777">
								<tui-list-cell :hover="false" :arrow="false">
									<view class="addmessage">
										 <view class="padding text-center">
										        <view class="padding-lr bg-white margin-top">
					
										            <view class="solid-bottom padding" >
										                  •  支出<text class="text-price">
															  <text style="color: red;">80.00</text>
														  &nbsp;&nbsp;&nbsp;&nbsp;购买
														  <text style="color: red;">计算器</text>
														  &nbsp;&nbsp;&nbsp;&nbsp;2023/10/22</text>
														<!-- <view class="padding" style="font-size: 1px;">2023/10/22</view> -->
													</view>
										        </view>
												<view class="padding-lr bg-white margin-top">
													
												    <view class="solid-bottom padding" >
												          •  支出<text class="text-price">
															  <text style="color: red;">21.50</text>
															  &nbsp;&nbsp;&nbsp;&nbsp;购买
															  <text style="color: red;">字典</text>
															  &nbsp;&nbsp;&nbsp;&nbsp;2023/10/23</text>
														  <!-- <view class="padding" style="font-size: 1px;">2023/10/26</view> -->
												    </view>
												</view>
												<view class="padding-lr bg-white margin-top">
													
												    <view class="solid-bottom padding" >
												         •   支出<text class="text-price">
															 <text style="color: red;">237.00</text>
															 &nbsp;&nbsp;&nbsp;&nbsp;购买
															 <text style="color: red;">棉鞋</text>
															 &nbsp;&nbsp;&nbsp;&nbsp;2023/10/26</text>
														 <!-- <view class="padding" style="font-size: 1px;">2023/11/2</view> -->
												    </view>
												</view>
										    </view>
									</view>
								</tui-list-cell>
							</tui-list-view>
						</view>
			<view class="pointcontainer">
				<tui-divider dividerColor="red" width="80%" ><h3 style="color: #f87e50;">积分变化动态折线图</h3></tui-divider>
	
				
				<tui-list-view title="积分变化" color="#777">
					<tui-list-cell :hover="false" :arrow="false">
						<view class="charts-box">
					  <qiun-data-charts 
					    type="line"
					    :opts="opts"
					    :chartData="chartData"
					  />
					</view>
					</tui-list-cell>
				</tui-list-view>
					
				
			</view>
		</view>
</template>

<script>
	import tuiFooter from "@/components/thorui/tui-footer/tui-footer.vue"
	import tuiDivider from "@/components/thorui/tui-divider/tui-divider.vue"
	import tuiListCell from "@/components/thorui/tui-list-cell/tui-list-cell.vue"
	export default {
		components:{
						tuiListCell,
						tuiDivider,
						tuiFooter
					},
		data() {
			return {
				head_photo: "",
				isFollowed: false, // 初始状态为未关注
				cardWidth: 350, // 卡片宽度，可调整
				posX: 10, // X轴位置，可调整
				posY: -30, // Y轴位置，可调整
				item: null,
				isFollowed: false,
				
				childId:0,
				donaterId:1,
				musttask_f:0,
								musttask_t:0,
								opttask_f:0,
								opttask_t:0,
								process1:0,
								process2:0,
								point:0,
								chartData: {},
								//您可以通过修改 config-ucharts.js 文件中下标为 ['line'] 的节点来配置全局默认参数，如都是默认参数，此处可以不传 opts 。实际应用过程中 opts 只需传入与全局默认参数中不一致的【某一个属性】即可实现同类型的图表显示不同的样式，达到页面简洁的需求。
								opts: {
								  color: ["#1890FF","#91CB74","#FAC858","#EE6666","#73C0DE","#3CA272","#FC8452","#9A60B4","#ea7ccc"],
								  padding: [15,10,0,15],
								  enableScroll: false,
								  legend: {},
								  xAxis: {
								    disableGrid: true
								  },
								  yAxis: {
								    gridType: "dash",
								    dashLength: 2
								  },
								  extra: {
								    line: {
								      type: "straight",
								      width: 2,
								      activeType: "hollow"
								    }
								  }
								}
			};
		},
		computed: {
		    followText() {
		      // 根据关注状态返回相应的文字
		      return this.isFollowed ? '已关注' : '关注';
		    },
		  },
		onLoad(options) {
				const b =JSON.parse(options.data)
				 console.log(b,'=-->')
				 this.dataList(b)
				 this.taskList(b)
				 this.PointList(b)
				 this.getServerData(b)
				 this.getHeadPhoto(b)
				},
		methods:{
			getHeadPhoto(v){
				this.head_photo = v.image
			},
			getServerData(v) {
						  var list=[];
						  var list1=[];
						  uni.request({
						    url: `http://localhost:8085/child/getLineChart?id=${v.id}`,
						    method: 'GET',
						    header: {
						      'Content-Type': 'application/json',
						    },
						    success: (res) => {
						      if (res.statusCode === 200) {
						        console.log(res.data)
								list=res.data.message[0]
								console.log(list)
						      } else {
						        // 处理请求失败的情况
						        console.error('请求失败');
						      }
						    },
						    fail: (error) => {
						      // 处理请求失败的情况
						      console.error('请求失败', error);
						    }
						  });
						  setTimeout(() => {
						    let res = {
						        categories: ["6月","7月","8月","9月","10月"],
						        series: [
						          {
						            name: "月度积分获取",
						            data: [list["6月"],list["7月"],list["8月"],list["9月"],list["10月"]]
						          },
								  {
								    name: "积分获取平均水平",
								    data: [58,81,75,63,81]
								  }
						        ]
						      };
						    this.chartData = JSON.parse(JSON.stringify(res));
						  }, 500);
						},
			
			
			async ifLiked(v){
							console.log(v,'ifLiked');
							try{
								console.log(v.id)
								// console.log(`http://localhost:8085/child/like?donateId=1&childId=${v.id}`)
								const result = await uni.request({
									url:`http://localhost:8085/child/like?donateId=${this.donaterId}&childId=${v.id}`,
									method:'GET',
									header: {
										'Content-Type':'application/json',
									}
								})
								console.log(result)
								if(result.data.code == "500" && result.data.msg == "该用户或该孩子不存在" ){
									return
								}
								const { code,message } = result.data
								// if(message.)
								// this.item = message
								// console.log("12312321")
								console.log(message)
								if(message.isLiked == 1){
									this.isFollowed = true
								}else{
									this.isFollowed = false
								}
								console.log(this.isFollowed)
							}catch(err){
								console.log(err);
							}
						},
			async dataList(v){
				// console.log(v,'------------aa');
				try{
					const result = await uni.request({
						url:`http://localhost:8085/child/childAll?id=${v.id}`,
						method:'GET',
						header: {
							'Content-Type':'application/json',
						}
					})
					console.log(result)
					const { code,message } = result.data
					this.item = message
 					console.log(message,'--------asd');
				}catch(err){
					console.log(err);
				}
			},
			//doing..
			async taskList(v){
				try{
					const mustresult = await uni.request({
						url:`http://localhost:8085/child/getMustTask?id=${v.id}`,
						method:'GET',
						header: {
							'Content-Type':'application/json',
						}
					})
					this.musttask_f=mustresult.data.message[0].已完成
					this.musttask_t=mustresult.data.message[0].总数
					this.process1=Math.round(this.musttask_f/this.musttask_t*100)
				}catch(err){
					console.log(err);
				}
				try{
					const optresult = await uni.request({
											url:`http://localhost:8085/child/getOptTask?id=${v.id}`,
											method:'GET',
											header: {
												'Content-Type':'application/json',
											}
										})
					this.opttask_f=optresult.data.message[0].已完成
					this.opttask_t=optresult.data.message[0].总数
					this.process2=Math.round(this.opttask_f/this.opttask_t*100)
				}catch(err){
					console.log(err);
				}
			},
			async PointList(v){
				try{
					const pointresult = await uni.request({
						url:`http://localhost:8085/child/getPoints?id=${v.id}`,
						method:'GET',
						header: {
							'Content-Type':'application/json',
						}
					})
					console.log(pointresult)
					// const { code,message } = pointresult.data
					this.point=pointresult.data.message
					console.log(this.point)
				}catch(err){
					console.log(err);
				}
			},
			//
			async follow() {
			      if(this.isFollowed == true){
			      					  this.isFollowed = !this.isFollowed
			      					  // 取消关注
			      					  
			      					  	const result = await uni.request({
			      					  		url:`http://localhost:8085/child/unConcernRecord?donateId=${this.donaterId}&childId=${this.childId}`,
			      					  		method:'GET',
			      					  		header: {
			      					  			'Content-Type':'application/json',
			      					  		}
			      					  	})
			      					  	const { code,message } = result.data
			      						
			      				  }else{
			      					  //关注
			      					  this.isFollowed = !this.isFollowed
			      					 
			      					  	const result = await uni.request({
			      					  		url:`http://localhost:8085/child/writeConcernRecord?donateId=${this.donaterId}&childId=${this.childId}`,
			      					  		method:'GET',
			      					  		header: {
			      					  			'Content-Type':'application/json',
			      					  		}
			      					  	})
			      					  	console.log(result.data)
			      					  	const { code,message } = result.data
			      						
			      				  }
			    },
			changeTag(b){
				console.log(JSON.parse(b),'-------->');
				return JSON.parse(b)
					
			}
			
			
		}
		

	}
</script>

<style lang="scss">
	.grid-container {
	  display: grid;
	  grid-template-columns: auto auto;
	  align-items: center;
	  gap: 10px; /* 可根据需要调整间距 */
	}
	
	.grid-container img {
	  width: 35px; /* 根据需要调整图像的宽度 */
	  height: auto;
	}
	
	.grid-container h3 {
	  margin: 0;
	}


	.taskcontainer {
	  border: 1px solid #ccc; /* 边框样式 */
	  border-radius: 10px; /* 圆角半径 */
	  padding: 2.4px; /* 内边距，可根据需要调整 */
	  background-color: #f0f0f0; /* 背景颜色，可根据需要调整 */
	  box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.2); /* 阴影效果，可根据需要调整 */
	}

.progress-container {
  width: 100%;
  padding: 20px; /* 添加适当的内边距 */
  background-color: #ffffff; /* 容器背景颜色 */
  border: 1px solid #ffffff; /* 添加边框 */
  border-radius: 15px; /* 圆角边框 */
}

.static-progress {
  width: 100%;
  height: 10px;
  background-color: #ccc;
}

.static-progress1-fill {
  height: 100%;
  background-color: #fa7f4b; /* 进度条颜色 */
}


.static-progress2-fill {
  height: 100%;
  background-color: #fb6773; /* 进度条颜色 */
  // width: 50%; /* 静态进度百分比 */
}

.task-container {
  display: flex; /* 使用弹性盒子布局 */
  justify-content: space-between; /* 在容器内部水平对齐 */
}

.task {
  font-weight: bold; /* 加粗文本 */
  margin-right: 110px; /* 设置右侧间距，可以根据需要调整 */
}

.tasknumber {
  width: 100%;
  max-width: 800px; /* 设置容器的最大宽度 */
  margin: 0 auto; /* 水平居中 */
  padding: 20px; /* 设置内边距 */
  background-color: #f0f0f0; /* 背景颜色 */
  border: 1px solid #ccc; /* 边框 */
  border-radius: 5px; /* 圆角 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 阴影 */
}

.page {
  display: flex;
  flex-direction: column;
  background-color: #f8f8f8;
}


.info-block {
  background-color: #ffffff;
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0 0 20px rgba(0,0,0,0.1);

}



.info-item:not(:last-child) {
  margin-bottom: 10rpx;
}

.info-title {
  font-weight: bold;
  margin-right: 8rpx;
  font-size: 34rpx;
}

.info-content {
  color: #555;
  font-size: 34rpx;
}


.tags {
  display: flex;
  flex-wrap: wrap;
  margin-bottom: 10px; /* 标签与下一个信息项之间的间距 */
  padding-top: 10rpx;
}

.tag {
  background-color: #f5a444; /* 浅橙色背景，可根据设计要求调整 */
  color: white;
  padding: 5px 10px;
  border-radius: 15px; /* 圆角 */
  font-size: 16px;
  margin-right: 10px; /* 标签之间的间距 */
  margin-bottom: 5px; /* 多行标签之间的垂直间距 */
}
.details-link {
  color: #e8a26f; /* 设置链接颜色 */
  margin-top: -40rpx;
  margin-left: 270px; /* 与组织名称保持适当间距 */
  text-decoration: underline; /* 添加下划线以突出显示为链接 */
}
.button-container {
	
  display: flex;
  flex-direction: row; /* align buttons horizontally */
  margin-top: -10px; /* or the amount of space you want between the card and the buttons */
  padding-left: 75rpx;
}

.image-class {
  width: 25px; /* 或者其他尺寸 */
  height: 25px; /* 或者其他尺寸 */
  margin-right: 5px; /* 如果需要在图片和文字之间有间距 */
}

.button {
 background-image: linear-gradient(to bottom, #2bdc9e, #14d0cc, #03c6eb);
  color: white;
  font-size: 35rpx;
  padding: 10px 15px;
  border-radius: 50px; /* 圆角按钮 */
  margin-bottom: 20px; /* 按钮之间的间距 */
  margin-top: -20px;
  margin-right: 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3); /* 按钮阴影 */
}

.gbutton-container {
flex-direction: column;
  display: flex;
  flex-direction: row; /* align buttons horizontally */
 margin-bottom: 0px; /* 按钮之间的间距 */
 margin-top: -20px; /* or the amount of space you want between the card and the buttons */
  padding-left: 410rpx;
}
.image-guanzhu {
  width: 25px; /* 或者其他尺寸 */
  height: 25px; /* 或者其他尺寸 */
  margin-right: 5px; /* 如果需要在图片和文字之间有间距 */
}

.button-guanzhu {
 background-image: linear-gradient(to bottom, #fc686c, #fb628b, #f95da0);
  color: white;
  font-size: 35rpx;
  padding: 10px 15px;
  border-radius: 50px; /* 圆角按钮 */
  margin-bottom: 20px; /* 按钮之间的间距 */
  margin-top: -49px;
  margin-right: 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3); /* 按钮阴影 */
}


.chat-link {
  color: #e8a26f; /* 设置链接颜色，您可以根据您的设计调整 */
  text-decoration: underline; /* 添加下划线以突出显示为链接 */
  margin-left: 270px;
   margin-top: -40rpx;
}

	
.box{
	display: flex;
	flex-wrap: nowrap;
	background-image: linear-gradient(to bottom, #fa804e, #fc8961, #fc9075);
	border-radius: 0 0 30px 30px; /* 左下和右下圆角，上边和左边为直角 */
	padding: 10px;
	// width: 400px !important;
	height: 350rpx;
	.icontainer{
		
		.ninfo-item{
			width:160px;
			.info-content{
				color: #f0f0f0;
				font-size: 70rpx;
			}
		}
		.ainfo-item{
			padding-top: 20rpx;
			.info-age{
				color: #f0f0f0;
				font-size: 40rpx;
				
			}
		}
		.sinfo-item{
			padding-top: 20rpx;
			.info-school{
				color: #f0f0f0;
				font-size: 40rpx;
				
			}
		}
		.timage{
			width: 150px;
			height: 150px;
			margin-left: 380rpx;
			margin-top: -500rpx;
		}
		
		
		
		
	
	    
	
}

.card-container {
  flex-direction: column;
  border: 1px solid #ccc;
  border-radius: 8px;
  box-shadow: 0 10px 10px rgba(0,0,0,0.1);
  background-color: #fff;
  /* 使用margin来控制卡片的位置，或者使用transform: translate(x, y) */
  margin-top: 10px !important;
  margin-left: 10px;
  /* 卡片的位置可以通过计算或传递参数来动态设置 */
  font-size: 30rpx;
}

}


</style>
