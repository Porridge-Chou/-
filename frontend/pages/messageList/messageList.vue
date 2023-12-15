<template>
	<view>
		<scroll-view class="container" :scroll-y="true"
		id="scroll-view" :scroll-with-animation="true"
		:scroll-top="scrollTop"
		:style="{height: `${windowsHeight}rpx`}">
			<view id="msgListview" class="chat-body">
				<view v-for="(item,index) in messageList"  @click="goToChat(item,index)">
					<view class="chat-item">
						<view class="avator">
							<image mode="scaleToFill" :src="getPic(index)"></image>
						</view>
						
						<view class="message-body">
							<view class="message-title">
								<view class="message-name">{{ item.key == item.msg.fromId?item.msg.name:item.msg.rname }}</view>
								<view class="message-time">{{ getTime(item.msg.time) }}</view>
							</view>
							<text class="message-content">{{ item.msg.content}}</text>
						</view>
					</view>
				</view>
			</view>
		</scroll-view>
	</view>
</template>

<script>
	import moment from 'moment'
	import 'moment/locale/zh-cn'
	
	
	//请求url
	const rurl='http://localhost:8085'
	const socketurl='ws://localhost:8088/ws'
	
	export default {
		data() {
			return {
				//键盘高度
				keyboardHeight:0,
				//聊天框高度
				bottomHeight:0,
				scrollTop: 0,
				messageList:[],
				connecting: false, //判断是否已经连接上websocket
				fromId:'56d0b7d81ba845e7a46c057a71895511',
				name:'测试人员',
				connected: false,
				timer:'',
				avatarList:['chat1.JPG','chat2.JPG','chat3.JPG','chat4.JPG','chat5.JPG'],
				
			}
		},
		computed:{
			windowsHeight(){
				return this.rpxTopx(uni.getSystemInfoSync().windowHeight)
			}
		},
		mounted() {
		    // 在组件挂载后，从后端获取用户列表数据，并将其赋值给userList
		    //this.fetchUserList();
		},
		onLoad() {
			
		if(uni.getStorageSync("id") !=null){
			this.fromId=uni.getStorageSync("id");
		}	
			uni.request({
				url:rurl+"/message/getMsgName",
				method:'POST',
				dataType:'json',
				data: this.fromId,
				header:{
				'Content-Type':'application/json',
				},
				success: (res) => {
					this.name=res.data.msg
				},
				})
			
			
		},
		onHide() {
			uni.closeSocket({
				code:1000,
				reason:'leave',
			})
			this.connected=false;
		},
		onShow(){
			//监听WebSocket连接成功
			uni.onSocketOpen((res)=>{
				uni.sendSocketMessage({
										 data:JSON.stringify({
											action:1,
											chatMessage:{
												id:'',
												fromId: this.fromId,
												toId:'',
												name:'',
												 time: moment().format("YYYY-MM-DD HH:mm:SS"),
												content:this.val,
											},
											extand:""
										}),
										sucess(res){},
										fail(err) {}
										})
								uni.hideLoading()
								console.log("连接成功")
								//定时发送心跳包
								this.timer=setInterval(this.keepalive,10000)
								this.connected=true
								this.connecting=false
								this.getSnapShot()
			})
			
			
			//监听收到WebSocket消息
			uni.onSocketMessage((res)=>{
				
				if(res.data!=null){
					var result = JSON.parse(res.data)
					}
				//console.log(result);
				if(parseInt(result.action) == 2){
					this.updateMessageList(result.chatMessage)
				}else if(parseInt(result.action) ==5){
					//暂时不知道
				}
				
			})
			
			//监听WebSocket关闭
			uni.onSocketClose((res)=>{
				console.log("socket断开")
				this.connected = false;
				clearInterval(this.timer)
			})
			
			
			this.connect();
		},
		methods: {
			//获取图片
			getPic(index){
				var len=this.avatarList.length;
				return '../../static/img/'+this.avatarList[index%len];
			},
			// px转换成rpx
			rpxTopx(px){
				let deviceWidth = wx.getSystemInfoSync().windowWidth
				let rpx = ( 750 / deviceWidth ) * Number(px)
				return Math.floor(rpx)
			},
			//生成时间
			getTime(timestamp){
				moment.locale('zh-cn')
				var nowdate=moment()
				var res= Math.round(nowdate.diff(timestamp)/1000);
				if(res<3600*24){
					return moment(timestamp).format("k:mm")
				}else if(res<3600*24*7){
					return moment(timestamp).format("dddd")
				}else{
					return moment(timestamp).format("MMM Do")
				}
			},
			//生成UUID的工具类
			guid() {
				return 'xxxxxxxxxxxx4xxxyxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
					var r = Math.random() * 16 | 0,
					v = c == 'x' ? r : (r & 0x3 | 0x8);
					return v.toString(16);
				});
			},
			gettime(str){
				moment(str).format()
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
				
				//定时更新快照
				this.getSnapShot()
			
			},
			//WebSocket初始化并连接
			connect(){
				if (this.connecting || this.connected) {
					return
				}
				uni.closeSocket({
					code:1000,
				})
				this.connecting = true;
				//加载弹窗
				uni.showLoading({
									title: '连接中...',
				})
				
				uni.connectSocket({
					url:socketurl,
					fail(err) {console.log('uni.connectSocket fail',err)}
				})
				
				this.getSnapShot();
				
			},
			//断开连接后重连
			reconnect(){
				if (this.connecting || this.connected) {
					return
				}
				this.connecting = true;
				uni.connectSocket({
					url:socketurl,
					fail(err) {console.log('uni.connectSocket fail',err)}
				})
				this.getSnapShot();
			},
			//获取消息快照
			getSnapShot(){
				//发出消息快照请求
				uni.request({
					url:rurl+'/message/getSnapShot',
					method:'POST',
					dataType:'json',
					data: this.fromId,
					header:{
						'Content-Type':'application/json',
						},
					success: (res) => {
						this.messageList=JSON.parse(res.data.msg);
						this.updateMessageList(null);
					}
				})
			},
			//收到消息后刷新消息列表
			updateMessageList(chat){
				//console.log(this.messageList)
				
				if(chat !=null){
					for(let i=0;i<this.messageList.length;i++){
						if(this.messageList[i].key == chat.fromId ||this.messageList[i].key == chat.toId){
							//志愿者发给捐助者的信息或捐助者发给志愿者的信息，信息都已知
							var sname=this.messageList[i].msg.name;
							var rname=this.messageList[i].msg.rname;
							this.messageList[i].msg=chat;
							if(chat.name == sname){
								chat.rname=rname;
							}else{
								chat.rname=sname;
							}
							
							this.messageList.reverse()
							this.messageList.sort((a,b)=>{
								return b.msg.time-a.msg.time
							})
							return
						}
					}
					//捐助者给未知志愿者发的消息和收取未知志愿者的消息，不在消息集合中，需要额外进行请求查询
					
					//需要查询的id
					var rqId= chat.fromId == this.fromId?chat.toId:chat.fromId;
					
					//发出请求寻找名字
					uni.request({
						url:rurl+"/message/getMsgName",
						method:'POST',
						dataType:'json',
						data: rqId,
						header:{
						'Content-Type':'application/json',
						},
						success: (res) => {
							if(chat.fromId == this.fromId){
								chat.rname=res.data.msg
							}else{
								chat.rname=this.name;
							}
							
						var newMsg=new Object()
						newMsg.key=rqId
						newMsg.msg=chat
						
						//添加元素
						this.messageList.push(newMsg)
						
						//重新排序数组
						this.messageList.sort((a,b)=>{
							return b.msg.time-a.msg.time
						})
					}
					})
					
				}
				this.messageList.sort((a,b)=>{
					return b.msg.time-a.msg.time
				})
			},
			goToChat(item,index) {
				var id=item.msg.fromId==this.fromId?item.msg.toId:item.msg.fromId
				
				//关闭socket连接
				uni.closeSocket({
					code:1000,
					reason:'leave',
				})
				
				console.log(id)
				uni.navigateTo({
					url:'/pages/chat/chat'+"?fromId="+this.fromId+"&toId="+id+"&index="+index,
				})
				
			  //console.log("fromId:   "+item.msg.fromId+"  toId:  "+item.msg.toId);
			},
		}
	}
</script>

<style lang="scss">
	$chatContentbgc:#F5F5F5;
	
	view,button,text,input,textarea {
			margin: 0;
			padding: 0;
			box-sizing: border-box;
		}
		
	.container{
		background-color: $chatContentbgc;
		
	}
	
	#msgListview{
		display: flex;
		flex-direction: column;
		margin-top: 23rpx;
		width: 100%;
		
		.chat-item{
			display: flex;
			flex-direction: row;
			height: 180rpx;
			width: 90%;
			margin-left: 5%;
			background-color: white;
			border-radius: 10rpx;
			margin-top: 20rpx;
			box-shadow: 2rpx 6rpx 3rpx #EAF0F0;
			overflow: hidden;
			.message-body{
				margin-left: 20rpx;
				
				width: 70%;
				.message-title{
					display: flex;
					flex-direction: row;
					margin-top: 25rpx;
					
					.message-name{
						font-size: 40rpx;
					}
					.message-time{
						font-size: 35rpx;
						margin-top: 5rpx;
						margin-right: 20rpx;
						margin-left: auto;
						color: #7B7C92;
					}
					
				}
				
				.message-content{
					margin-top: 17rpx;
					font-size: 35rpx;
					color: #686982;
					overflow: hidden;
					max-width: 100%;
					word-break: keep-all;
					display: flex;
					text-overflow: ellipsis;
					lines: 1;
					max-height: 43rpx;
				}
			}
			
			
			//头像样式
			.avator{
				 width: 140rpx;
				 display: flex;
				 margin-left: 20rpx;
				//justify-content: center;
				
				overflow: hidden;
				
				image{
					align-self: center;
					width: 140rpx;
					height: 140rpx;
					border-radius: 30rpx;
				}
			}
		}
		
	}
		
	
		
	
</style>
