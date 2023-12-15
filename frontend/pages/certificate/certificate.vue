<template>
	<view class="container">
		<view class="content">
			<canvas class="tui-cropper-canvas" canvas-id="shareCanvas"  ></canvas>
			
			 <!-- <view class="save-btn" @click="saveImage">保存图片</view> -->
`			<view class="bottom-box" >
				<view class="image-container">
					<image  class="image-frame" src="../../static/img/Frame.png" mode="heightFix" @click="downlodeimage"></image>
					<!-- <image  class="image-downlode"  src="../../static/img/downlode.png" mode="heightFix" @click="downlodeimage"></image>
					<image  class="image-weixinhaoyou"  src="../../static/img/weixinhaoyou.png" mode="heightFix"></image>
					<image  class="image-pengyouquan"  src="../../static/img/pengyouquan.png" mode="heightFix"></image>
					<image  class="image-qq"  src="../../static/img/qq.png" mode="heightFix"></image>
					<image  class="image-shareto"  src="../../static/img/shareto.png" mode="heightFix"></image> -->
				</view>
				
			</view>
		</view>	
	</view>
	
</template>

<script>
let username ;
export default {
	data(){
		return {
			name : '',
			donatename : '',
			money:'',
			date:''
		}
	},
	
	//onload中不支持asyna语句,操作在方法中也需要
	async onLoad(options) {
	  if (options && options.data) {
	    const donateData = JSON.parse(decodeURIComponent(options.data));
	    // donateDate是传过来的数据
		this.donatename = donateData["name"];
		this.money = donateData["money"];
		this.date = donateData["data"];
		// console.log(donateData["name"]);
		// console.log(this.name);
		// console.log(typeof(this.name))
		// console.log(this.money);
		// console.log(typeof(this.money))
		const uid = uni.getStorageSync('id');
		console.log('用户的id是'+uid)
		await this.getDonateName(uid)
		this.drawShareImage();
		
	  }
	},
	methods: {
		//绘制实心圆
		drawEmptyRound(ctx,x,y,radius){
		    ctx.save()
		    ctx.beginPath();
		    ctx.arc(x, y, radius, 0, 2 * Math.PI,true);
		    ctx.setFillStyle('#F5F5F5')
		    ctx.fill();
		    ctx.restore()
		    ctx.closePath()
		},
		
		async drawShareImage() {
			// const uid = uni.getStorageSync('id');
			// console.log('用户的id是'+uid)
			
			
			// await this.getDonateName(uid)
			
			console.log('这里用户的名字是'+username)
			
			const ctx = uni.createCanvasContext('shareCanvas', this);
		
			ctx.globalAlpha = 1;
			//矩形框
			// ctx.strokeRect(40,13,250,);
			// 绘制文本
			let hW = uni.upx2px(600);
			let hH = uni.upx2px(450);
			
			this.drawEmptyRound(ctx,0,370,7)
			this.drawEmptyRound(ctx,337,370,7)
			//绘制虚线
			
			
			ctx.setLineDash([5,5]);
			ctx.strokeStyle = '#b5b5b5';
			ctx.beginPath();
			ctx.moveTo(7, 370);
			ctx.lineTo(330, 370);
			ctx.stroke();
			
			
			
			//绘制文案
			// 设置字体样式
			ctx.font = '24px  Arial' ;
			
			// 绘制填充文字
			ctx.fillStyle = "#000000"
			ctx.fillText('捐赠证书',110,50);
			
			ctx.font = '20px sans-serif '
			
			ctx.fillText(this.name,30,100);
			
			ctx.font = '15px sans-serif ';
			
			ctx.fillText(' 感谢您对慈善事业的关心与支持。慈善基',50,140);
			ctx.fillText(' 金会将遵照您的意愿，将捐赠的'+this.numberToChinese(this.money)+'圆',20,170);
			ctx.fillText('（年'+this.money+'）用于发展公益事业，帮助弱势群体。',20,200);
			ctx.fillText('感谢您的慈心善行，谨向您致以崇高敬意！',20,230);
			ctx.fillText('美德如瑜，上善若水。感谢您，让世界更温暖。',20,260);
			ctx.drawImage('../../static/img/yingzhang.png', 200, 280, 90, 90);
			ctx.fillText('明光筑梦平台',200,320);
			
			
			var [datePart, timePart] = this.date.split(' ');
			// console.log(datePart)
			
			var dateportion = new Date(datePart);
			
			// 获取年、月、日
			var year = dateportion.getFullYear();
			var month = dateportion.getMonth() + 1; // 月份是从0开始计数的，所以要加1
			var day = dateportion.getDate();
			// console.log( year + "年" + month + "月" + day + "日")
			ctx.fillText(year + "年" + month + "月" + day + "日",200,340);
			
			// 绘制描边文字
			//ctx.strokeText('世界', 50, 50);
		
			

			//ctx.draw();
		  //绘制完成后，调用toTempFilePath将canvas转换为图片并保存
		   ctx.draw(false, () => {
		     uni.canvasToTempFilePath({
		       canvasId: 'shareCanvas',
			    fileType: 'png', // 指定生成图片的格式为jpg
				quality: 1, // 图片的质量，范围为0-1
				success: (res) => {
		         // res.tempFilePath 为生成的图片路径，可以进行保存或分享
		         // console.log('生成的分享图路径:', res.tempFilePath);
				 this.tempFilePath = res.tempFilePath;
			
			/*code是指图片base64格式数据*/
			//声明文件系统
			
				// this.base64ToTempFilePath(this.tempFilePath , function(tempFilePath) {
				// 		 // console.log('转换成功，临时地址为:', tempFilePath)
				// 		  that.orderqrpath = tempFilePath // 会在canvas中调用
				// 		}, function() {
				// 		  console.log('转换失败')
				// 		})
		       },
		       fail: (err) => {
		         console.error('生成分享图失败:', err);
		       }
		     }, this);
		   });
		},
		downlodeimage(){
			const that = this;
			console.log('kaishi');
			uni.saveImageToPhotosAlbum({
				filePath: that.tempFilePath,
				success: (res) => {
				  console.log('保存成功');
				},
				fail: (err) => {
					uni.showToast({
						title:'保存成功',
						icon : 'none',
						duration:2000
					})
				}
			})
		},
		
		
		
		numberToChinese(num) {
		  const chineseNumArr = ['零','一','二','三','四','五','六','七','八','九'];
		  const unitArr = ['', '十', '百', '千', '万', '十', '百', '千', '亿', '十', '百', '千'];
		  const numStr = num.toString();
		  let result = '';
		  for (let i = 0; i < numStr.length; i++) {
		    const currentNum = parseInt(numStr[i]);
		    const unit = unitArr[numStr.length - 1 - i];
		    if (currentNum !== 0) {
		      result += chineseNumArr[currentNum] + unit;
		    } else {
		      // 处理连续的零
		      if (result[result.length - 1] !== '零') {
		        result += chineseNumArr[currentNum];
		      }
		    }
		  }
		  return result;
		},
		
		
		async getDonateName(uid) {
			try{
				const response = await uni.request({
					url: 'http://localhost:8085/donate/getDonateName', 
					method: 'POST',
					header: {
						'Content-Type': 'application/json' // 设置请求头部为JSON格式
					},
					data: {
						id: uid
					}
				});
				
				console.log('拿到了');
				console.log(response.data.donate.name) ;
				this.name = response.data.donate.name;
				username = response.data.donate.name;
			}catch(error){
				console.error(err);
			}
		}
		
		
		
		// base64ToTempFilePath(base64Data, success, fail) {
		// 		  const fs = uni.getFileSystemManager()
		// 		  const fileName = 'temp_image_' + Date.now() + '.png' // 自定义文件名，可根据需要修改
		// 		  const filePath = '../../static/img' + '/' + fileName
		// 		  const buffer = uni.base64ToArrayBuffer(base64Data)
		// 		  fs.writeFile({
		// 		    filePath,
		// 		    data: buffer,
		// 		    encoding: 'binary',
		// 		    success() {
		// 		      success && success(filePath)
		// 		    },
		// 		    fail() {
		// 		      fail && fail()
		// 		    }
		// 		  })
		// 	}
		
		
		//在小程序中可以进行图片保存
		// saveImage(){
		// 	const that = this;
		// 	console.log('kaishi');
		// 	uni.saveImageToPhotosAlbum({
		// 		filePath: that.tempFilePath,
		// 		success: (res) => {
		// 		  console.log('保存成功');
		// 		},
		// 		fail: (err) => {
		// 		  console.error('保存失败');
		// 		}
		// 	})
		// }
		
	  }
};

</script>



<style>
	page{
		background-color: #F5F5F5;
	}
	
	
	.higher-z-index {
	  z-index: 3; /* 设置较高的层级 */
	}
	
	.image-container{
		
		position: absolute;
		top: 1200rpx;
		left: 50rpx;
		height: 250rpx;
		width: 20.0875rem !important;
	}

	.image-frame{
		height: 100%;
	}
	
	
	.image-downlode{
		position: absolute;
		top: 55rpx;
		left: 0rpx;
		height: 230rpx;
	}
	
	.image-weixinhaoyou{
		position: absolute;
		top: 55rpx;
		left: 140rpx;
		height: 230rpx;
	}
	.image-pengyouquan{
		position: absolute;
		top: 55rpx;
		left:340rpx;
		height: 230rpx;
	}
	.image-qq{
		position: absolute;
		top: 55rpx;
		left: 480rpx;
		height: 230rpx;
	}
	.image-shareto{
		position: absolute;
		top: 0rpx;
		left: 160rpx;
		height: 75rpx;
	}
	
	.bottom-box{
		margin-top: 200rpx;
		height: 25%;
		width: 100%;
		background-color: white;
	}
	
	.tui-cropper-canvas{
		height: 60%; 
		width:90%;
		background-color: white;
		z-index: 1;
	}
	
	
	.content{
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		z-index: 9999999;
		
		display: flex;
		flex-direction: column;
		margin-top: 15%;
		align-items: center;
		width: 100%;
		z-index: 1;
	} 
</style>
