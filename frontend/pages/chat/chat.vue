<template>
	<view class="content">
	<scroll-view class="container" :scroll-y="true" 
		id="scroll-view" 
		:show-scrollbar="false"
		:scroll-with-animation="true"
		:scroll-top="scrollTop" 
		:style="{height: `${windowsHeight}rpx`}" >
		<view id="msgListview" class="chat-body">
		    <view v-for="(item) in messageList" :key="index">
				<!-- 自己发的消息 -->
				<view class="my-chat" v-if="item.fromId == fromId">
					<!-- 时间戳 -->
					<view class="chat-time">{{getTime(item.time)}}</view>
					<!-- 聊天内容 -->
					<view class="my-content">
						<view class="chat-bubble" >{{item.content}}</view>
						<!-- 头像 -->
						<view class="my-avatar">
							<image  mode="scaleToFill" src="../../static/a02942b44ed0754b2f366f1f41923ee0.jpeg"></image>
						</view>
					</view>
				</view>
				
				<!-- 对方发的消息 -->
				<view class="friend-chat" v-if="item.fromId != fromId">
					<!-- 时间戳 -->
					<view class="chat-time">{{getTime(item.time)}}</view>
					<!-- 聊天内容 -->
					<view class="friend-content">
						<view class="chat-bubble" >{{item.content}}</view>
						
						<!-- 头像 -->
						<view class="friend-avatar">
							<image  mode="scaleToFill" :src="getPic(this.index)"></image>
						</view>
					</view>
			</view>
			</view>
		</view>
	</scroll-view>
	
	
	
	<view class="bottom">		
	<view class="chat-bottom" :style="{bottom:`${keyboardHeight}rpx`}" >	
		<view class="send-audio">
			<!-- 按住说话按钮 -->
			<tuiButton type="white" shape="circle"  height="75rpx" width="75rpx">
				<tui-icon name="voice" color="#F0804A"></tui-icon></tuiButton>
		</view>
			<textarea class="chat-msg" v-model="val">{{val}}</textarea>
		<view class="sendmsg">
			<tuiButton type="white" shape="circle"  height="75rpx" width="75rpx" @click="send(val)">
				<tui-icon name="send" color="#F0804A"></tui-icon>
			</tuiButton>
		</view>
	</view>
	</view>
	</view>
</template>

<script>
	    import tuiButton from "@/components/thorui/tui-button/tui-button.vue"
		import tuiBubblePopup from "@/components/thorui/tui-bubble-popup/tui-bubble-popup.vue"
		import tuiIcon from "@/components/thorui/tui-icon/tui-icon.vue"
		import moment from 'moment'
		import 'moment/locale/zh-cn'
		
		
		const rurl='http://localhost:8085'
		const socketurl='http://localhost:8088/ws'
	
	
	export default {
		data() {
			return {
				//键盘高度
				keyboardHeight:0,
				//聊天框高度
				bottomHeight:0,
				scrollTop: 0,
	
				val: '',
				connecting: false, //判断是否已经连接上websocket
				connected: false,
				timer:'',
				Mname:'',
				Fname:'',
				messageList:[],
				fromId:'',
				toId:'',
				avatarList:['chat1.JPG','chat2.JPG','chat3.JPG','chat4.JPG','chat5.JPG'],
				index:0,
				
			}
		},
		computed:{
			windowsHeight(){
				return this.rpxTopx(uni.getSystemInfoSync().windowHeight)-130
			}
			
		},
		components:{
		    tuiButton,
			tuiBubblePopup,
			tuiIcon,
		},
		onShow(){
			
			//定义连接建立成功监听器
			uni.onSocketOpen((res)=>{
				
				uni.sendSocketMessage({
					 data:JSON.stringify({
						action:1,
						chatMessage:{
							id:'',
							fromId: this.fromId,
							toId: this.toId,
							name:'',
							 time: moment().format("YYYY-MM-DD HH:mm:SS"),
							content:this.val,
						},
						extand:""
					})
				})
				uni.hideLoading();
				console.log("连接成功")
				this.connected=true;
				this.connecting=false;
				//建立心跳链接
				this.timer=setInterval(this.keepalive,8000)
				
				
				
				//读取历史消息
				this.getHistoryMsg();
			})
			
			
			
			//定义消息接收器
			uni.onSocketMessage((res)=>{
				var result=JSON.parse(res.data);
				
				if(parseInt(result.action) ==2){
					//获取返回结果
					var msg=result.chatMessage
					//收到消息
					if(msg.fromId ==this.toId){
						this.messageList.push(msg);
						this.scrollToBottom()
					}
					
					//签收消息
					uni.sendSocketMessage({
						data:JSON.stringify({
							action:3,
							chatMessage:null,
							extand:this.fromId+","+this.toId,
					})
					})
					
				}else if(parseInt(result.action) ==5){
					
					this.messageList=(JSON.parse(result.arr))
					this.scrollToBottom()
				}
			})
		
		
			//定义socketClose关闭
			uni.onSocketClose((res)=>{
				console.log("socket连接关闭");
				clearInterval(this.timer)
				this.connected=false;
			})
			
		 	this.connect();
			
			console.log(this.messageList)
		
		
		
		},
		onLoad(options) {
			
			
			//接收到页面参数
			this.fromId=options.fromId
			this.toId=options.toId
			
			if(options.index !=null){
				this.index=options.index;
			}else{
				this.index=Math.floor(Math.random()*5)
			}
			
			console.log(this.fromId+"toId:   "+this.toId)
		},
		mounted() {
			//设置本方名称
			
			uni.request({
				url:rurl+"/message/getMsgName",
				method:'POST',
				dataType:'json',
				data: this.fromId,
				header:{
				'Content-Type':'application/json',
				},
				success: (res) => {
					this.Mname=res.data.msg
				}
			})
			
			
			//设置对方名字
			uni.request({
				url:rurl+"/message/getMsgName",
				method:'POST',
				dataType:'json',
				data: this.toId,
				header:{
				'Content-Type':'application/json',
				},
				success: (res) => {
					this.Fname=res.data.msg
					//设置标题
					
					// uni.setNavigationBarTitle({
					// 	title:this.Fname,
					// })
				}
			})
			
		},
		onBackPress(e) {
			this.connected=false;
			uni.closeSocket({
				code:1000
			})
		},
		onHide() {
			uni.closeSocket({
				code:1000,
				reason:'leave',
			})
			this.connected=false;
		},
		 methods: {
			 //获取图片
			 getPic(index){
			 	var len=this.avatarList.length;
			 	return '../../static/img/'+this.avatarList[index%len];
			 },
			//生成时间
			getTime(timestamp){
				moment().locale('zh-cn')
				var nowdate=moment()
				var res= Math.round(nowdate.diff(timestamp)/1000);
				if(res<60){
					return '';
				}else if(res<3600*24){
					return moment(timestamp).format("k:mm")
				}else if(res<3600*24*7){
					return moment(timestamp).format("dddd")
				}else{
					return moment(timestamp).format("MMM Do")
				}
			},
			scrollToBottom() {
			 		let query = uni.createSelectorQuery().in(this);
			 		    //获取节点信息
			 			query.select('#msgListview').boundingClientRect();
			 			query.select('.container').boundingClientRect();

			 			query.exec((res) =>{
							
			 				if(res[1].height > res[0].height){
			 		            this.$nextTick(()=>{
			 		                this.scrollTop = this.rpxTopx(res[1].height - res[0].height)*10
			 					})
			 				}
			 			})
						
			 		
			},
			// px转换成rpx
			rpxTopx(px){
				let deviceWidth = wx.getSystemInfoSync().windowWidth
				let rpx = ( 750 / deviceWidth ) * Number(px)
				return Math.floor(rpx)
			},
		//生成UUID工具类
			guid() {
				return 'xxxxxxxxxxxx4xxxyxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
		        var r = Math.random() * 16 | 0,
		            v = c == 'x' ? r : (r & 0x3 | 0x8);
		        return v.toString(16);
		    });
			},
			reconnect(){
				if (this.connecting || this.connected) {
					return
				}
				this.connecting = true
				
				//连接建立websocket
				uni.connectSocket({
					url: 'ws://localhost:8088/ws',
					fail(err) {
						console.log('uni.connectSocket fail', err)
					},
				})
				
				
			},
			getHistoryMsg(){
				uni.sendSocketMessage({
					//拉取历史信息
					 data:JSON.stringify({
						action:5,
						chatMessage:null,
						extand:this.fromId+","+this.toId,
					}),
					sucess(res){
							console.log("发送成功"+res);
					},
					fail(err) {
						console.log(err);
					}
				})
			},
			connect() {
				//判断是否已连接
				if (this.connecting || this.connected) {
					return
				}
				this.connecting = true
				
				//连接建立websocket
				uni.connectSocket({
					url: 'ws://localhost:8088/ws',
					// success(res) {
					// 	// 这里是接口调用成功的回调，不是连接成功的回调，请注意
					// 	console.log('uni.connectSocket success', res)
					// },
					fail(err) {
						// 这里是接口调用失败的回调，不是连接失败的回调，请注意
						console.log('uni.connectSocket fail', err)
					},
				})
			},
			//客户端保持心跳
			keepalive(){
				
				//发送心跳包
				uni.sendSocketMessage({
					 data:JSON.stringify({
						action:4,
						chatMessage:null,
						extand:"",
					}),
					sucess(res){
						console.log("发送心跳包")
					},
					fail(err) {
					}
				})
				
				//定时未读消息
			
			},
			//接收未读消息
			getUnreadMsg(){
				//发出未读信息请求
				//console.log("发出请求")
				uni.request({
					url:rurl+'/message/getUnreadMsg',
					method: 'POST',
					dataType: 'json',
					data: {
						sId:this.fromId,
						rId:this.toId,
						},
					header:{
							'Content-Type':'application/json',
						},
				}).then((res)=>{
					
					
					
					
					var result= res[1].data;
					console.log(result)
					this.messageList=JSON.parse(result.msg)
				})
			},
			//发送消息
			send(val) {
				console.log("发送消息")
				
				//重连机制
				if(this.connected == false){
					this.reconnect();
					uni.showToast({
						title:"连接失败，请重试",
						icon:'error',
					})
					return;
				}
				
				//字符串监测
				if(val.length ==0){
					return;
				}
				
				this.val=''
				
				this.messageList.push({
							id:'',
							fromId: this.fromId,
							toId: this.toId,
							name:this.Mname,
							 time: moment().valueOf(),
							content:val,
						})
						
				
				
				
				uni.sendSocketMessage({
					 data:JSON.stringify({
						action:2,
						chatMessage:{
							id:'',
							fromId: this.fromId,
							toId: this.toId,
							name:this.Mname,
							 time: moment().format("YYYY-MM-DD HH:mm:SS"),
							content:val,
						},
						extand:""
					}),
					sucess(res){
						
					},
					fail(err) {
						console.log('消息发送失败')
					}
				})
			
				
			},
			
		}
	}
</script>

<style lang="scss">
	
	$chatContentbgc:#F5F5F5;
	$sendBtnbgc: #4F7DF5;
	
	view,button,text,input,textarea {
			margin: 0;
			padding: 0;
			box-sizing: border-box;
		}
		
	.container{
		background-color: $chatContentbgc;
		
	}
	::-webkit-scrollbar{
		display: none;
		width: 0 ;
		height: 0 ;
		//-webkit-appearance: none;
		background: transparent;
		color: transparent;
	}
	
	#msgListview{
		display: flex;
		flex-direction: column;
		padding-top: 23rpx;
		.my-chat{
			//margin-top: 20rpx;
			
			.chat-time{//时间戳配置
				display: flex;
				justify-content: center;
			}
			
			.my-content{
				flex-direction: row;
				justify-content: flex-end;
				display: flex;
				padding: 13rpx 30rpx;
				position: relative;
				//聊天气泡配置
				.chat-bubble{
					background-color: white;
					margin-top: 25rpx;
					border-radius: 15rpx;
					margin-right: 15rpx;
					max-width: 486rpx;
					font-size: 32rpx;
					padding: 24rpx 24rpx;
					font-weight: 500;
					line-height: 42rpx;
					word-wrap: break-word;
					box-shadow: 2rpx 6rpx 3rpx #EAF0F0;
				}
				//消息三角形
				.chat-bubble::after{
					position: absolute;
					display: inline-block;
					content: '';
					width: 0;
					height: 0;
					right: 120rpx;
					top: 60rpx;
					border: 15rpx solid transparent;
					border-left: 15rpx solid white;
				}
			}
			
			
			
			.my-avatar{
				 width: 100rpx;
				 height: 100rpx;
				 display: flex;
				 margin-bottom: 10rpx;
				 justify-content: center;
				border-radius: 25rpx;
				overflow: hidden;
				
				image{
					align-self: center;
					width: 100rpx;
					height: 100rpx;
				}
			}
			
		
		}
		
		
		.friend-chat{
			
			.friend-content{
				flex-direction: row-reverse;
				justify-content: flex-end;
				display: flex;
				padding: 13rpx 23rpx;
				position: relative;
				
				
				//聊天气泡配置
				.chat-bubble{
					background-color: white;
					margin-top: 25rpx;
					border-radius: 15rpx;
					margin-left: 15rpx;
					max-width: 486rpx;
					font-size: 32rpx;
					padding: 24rpx 24rpx;
					font-weight: 500;
					line-height: 42rpx;
					word-wrap: break-word;
					box-shadow: 2rpx 6rpx 3rpx #EAF0F0;
				}
				
				//消息三角形
				.chat-bubble::before{
					position: absolute;
					display: inline-block;
					content: '';
					width: 0;
					height: 0;
					left: 113rpx;
					top: 65rpx;
					border: 15rpx solid transparent;
					border-right: 15rpx solid white;
				}
				
				
			}
			
			.friend-avatar{
				width: 100rpx;
				 height: 100rpx;
				 display: flex;
				 margin-bottom: 10rpx;
				 justify-content: center;
				border-radius: 25rpx;
				overflow: hidden;
				
				image{
					align-self: center;
					width: 100rpx;
					height: 100rpx;
				}
			}
			
			
			.chat-time{//时间戳配置
				display: flex;
				justify-content: center;
			}
		}
	}
	
	  
	  
	  .bottom{
		  width: 100%;
		  height: 130rpx;
	  }
	  
	  .chat-bottom{
		  width: 100%;
		  height: 130rpx;
		  max-height: 200rpx;
		  background-color: #FA7F4B;
		  display: flex;
		flex-direction: row;
		  position: fixed;
		  bottom: 0;
		  
		  .send-audio{
			  display: flex;
			  margin-top: 25rpx;
			  margin-left: 10rpx;
			  margin-right: 15rpx;
			  justify-content: center;
			  width: 75rpx;
			  height: 75rpx;
			  
			  
			  
		  }
		  
		  .chat-msg {
		  					width: 537rpx;
		  					min-height: 80rpx;
							height: 80rpx;
							max-height: 300rpx;
		  					background: #FFFFFF;
		  					border-radius: 25rpx;
		  					font-size: 38rpx;
		  					font-family: PingFang SC;
		  					color: #333333;
		  					line-height: 38rpx;
							margin-top: 20rpx;
							margin-bottom: 25rpx;
		  					padding: 8rpx 8rpx;
		  				}
		  
		  
	  }

		  .sendmsg{
			  display: flex;
			  margin-top: 25rpx;
			  margin-left: 10rpx;
			  margin-right: 15rpx;
			  justify-content: center;
			  width: 75rpx;
			  height: 75rpx;
			  overflow: hidden;
		  }
	
</style>
