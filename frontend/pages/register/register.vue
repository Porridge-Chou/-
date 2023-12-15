<template>
	<view class="register">
		<view class="container">
		<image class="logo" src="/static/img/logo.png"></image>
		  <form>
		    <view class="username-input">
		      <input type="text" name="username" placeholder="   请输入账号" v-model="username">
		    </view>
		    <view class="password-input">
		      <input type="password" name="password" placeholder="    请输入密码" v-model="password">
		    </view>
		    <view class="repassword-input">
		      <input type="password" name="confirmPassword" placeholder="    再次输入密码" v-model="confirmPassword">
		    </view>
			
		    <picker name="role" :value="roleIndex" @change="bindPickerChange" :range="roles">
			
		      <view class="picker" style="display: flex;">
				  <image src="/static/img/role.png" class="icon" 
				  style="margin-top: 2px;margin-left: 3px;"></image> 
					<div style="margin-top:4px;margin-left:10px">
						身份信息：{{roles[roleIndex]}}
					</div>
				</view>
		    </picker>
			
		    <button class="btn-register" type="submit" @click.prevent="register">注册 →</button>
		  </form>
		  <view class="footer">
		    <text style="white-space: nowrap;">已有账号？</text>
		    <navigator url="/pages/login/login">
		      <text class="link-text">前去登录</text>
		    </navigator>
		  </view>
		</view>
	</view>
  
</template>

<script>
export default {
  data() {
    return {
      username: '',
      password: '',
      confirmPassword: '',
      roles: ['志愿者', '受捐助者',' 捐助者'],
      roleIndex: 0
    }
  },
  methods: {
    bindPickerChange(e) {
      this.roleIndex = e.detail.value
    },
    register() {
		const confirmPassword = this.confirmPassword;
		const params = {
			username: this.username,
			password: this.password,
			roleIndex: this.roleIndex
		};
		console.log(params);
		if(params.username==''){
			uni.showToast({
			  title: '请输入账号',
			  icon: 'none',
			  duration: 2000
			});
		}else if(params.password==''){
			uni.showToast({
			  title: '请输入密码',
			  icon: 'none',
			  duration: 2000
			});
		}else if(params.password!=confirmPassword){
			uni.showToast({
			  title: '两次输入密码不一致',
			  icon: 'none',
			  duration: 2000
			});
		}else{
			uni.request({
				url: 'http://localhost:8080/user/register',
				method: 'POST',
				header: {
				    'Content-Type': 'application/json' // 设置请求头部为JSON格式
				  },
				data: params,
				 // 根据后端返回的数据进行相应的处理
				success: (res) => {
				 // 成功处理请求
				  console.log(res.data);
				  if(res.data.code == 0){
					  uni.showToast({
					    title: '注册成功',
					    icon: 'none',
					  	duration: 2000
					  });
				  }else if(res.data.code == 500){
					  uni.showToast({
					    title: '该用户已注册',
					    icon: 'none',
					  	duration: 2000
					  });
				  }
				 
				},
				 
				 
				 
			});
		}
    }
  }
};
</script>

<style>
.container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;
}


	.form {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
	}
	.logo {
		width: 200px;
		height: 200px;
		margin-bottom: 50px;
	}
	.logo,.username-input{
		margin-bottom: 60px; /* 添加下边距 */
	}
	.icon {
	  width: 22px; /* 设置图标宽度 */
	  height: 22px; /* 设置图标高度 */
	  margin-right: 5px; /* 设置图标右边距 */
	}


	.username-input {
		width: 100%;
		padding: 20rpx;
		border: 1rpx solid #000000;
		border-radius: 15px;
		background-image: url('/static/img/user.png');
		background-position: 10px center; /* 调整图标的位置 */
		background-repeat: no-repeat;
		padding-left: 30px; /* 留出图标的空间 */

	}
	.password-input {
		width: 100%;
		padding: 20rpx;
		border: 1rpx solid #000000;
		border-radius: 15px;
		background-image: url('/static/img/lock.png');
		background-position: 10px center; /* 调整图标的位置 */
		background-repeat: no-repeat;
		padding-left: 30px; /* 留出图标的空间 */
	
	}
	.repassword-input {
		width: 100%;
		padding: 20rpx;
		border: 1rpx solid #000000;
		border-radius: 15px;
		background-image: url('/static/img/douLock.png');
		background-position: 10px center; /* 调整图标的位置 */
		background-repeat: no-repeat;
		padding-left: 30px; /* 留出图标的空间 */
	
	}
	
	.picker {
	
	  width: 100%;
	  border-radius: 30rpx;
	  padding: 16rpx;
	  height: 100rpx;
	  border: 1rpx solid #000000;
	}
	.picker,.repassword-input{
		margin-bottom: 20px; 
	}
	
	.username-input, .password-input {
		margin-bottom: 20px; /* 添加下边距 */
	}
	
	





.btn-register {
  background: linear-gradient(to bottom, #FF8653,  #FAA94B);
  color: #fff;
  border-radius: 4rpx;
  padding: 8rpx;
  margin-top: 16rpx;
  width: 80%;
  border-radius: 30px
}

.footer {
  display: flex;
}
.link-text {
	  color: #FF8653;
	  text-decoration: underline;
	}

.register{
	background-size: contain;
}

.btn-register,.footer{
		margin-bottom: 20px; /* 添加下边距 */
	}

</style>
