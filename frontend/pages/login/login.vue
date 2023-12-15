<template>
	<view class="login">
		<view class="container">
			<image class="logo" src="/static/img/logo.png"></image>
			<view class="form">
				<view class="username-input">
					<input type="text" name="username" placeholder="  请输入账号" v-model="username">
				</view>
				<view class="password-input">
					<input type="password" name="password" placeholder="  请输入密码" v-model="password">
				</view>
				
				<button class="btn-login" type="submit" @click.prevent="login">登录 →</button>
			</view>
			<view class="footer">
			  <text style="white-space: nowrap;">没有账号？</text>
			  <navigator url="/pages/register/register">
			    <text class="link-text">前去注册</text>
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
				password: ''
			};
		},
		methods: {
			login() {
				// 构造请求参数
				const params = {
					username: this.username,
					password: this.password
				};
				if (params.username == '') {
					uni.showToast({
						title: '请输入账号',
						icon: 'none',
						duration: 2000
					});
				} else if (params.password == '') {
					uni.showToast({
						title: '请输入密码',
						icon: 'none',
						duration: 2000
					});
				} else {
					// 发送请求到后端
					// 使用uni.request或者axios等库发送POST请求
					uni.request({
						url: 'http://localhost:8085/user/login',
						method: 'POST',
						header: {
							'Content-Type': 'application/json' // 设置请求头部为JSON格式
						},
						data: params,
						// 根据后端返回的数据进行相应的处理，code = 0代表登陆成功 code=500代表密码错误 code=600代表用户不存在
						success: (res) => {
							// 成功处理请求
							if (res.data.code === 0) {
								console.log('请求成功');
								console.log(res.data);
								const id = res.data.id;
								uni.setStorageSync('id', id);

								uni.switchTab({
									url: '/pages/index/index',
									success: function() {
										console.log('跳转成功');
									},
									fail: function(err) {
										console.error('跳转失败:', err);
									}
								});

							} else if (res.data.code === 500) {
								// 密码错误提示
								uni.showToast({
									title: '密码错误',
									icon: 'none',
									duration: 2000
								});
							} else if (res.data.code === 600) {
								// 用户未找到提示
								uni.showToast({
									title: '用户未找到',
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
	.username-input, .password-input {
		margin-bottom: 20px; /* 添加下边距 */
	}

	.username-input {
		width: 80%;
		padding: 20rpx;
		border: 1rpx solid #000000;
		border-radius: 15px;
		background-image: url('/static/img/user.png');
		background-position: 10px center; /* 调整图标的位置 */
		background-repeat: no-repeat;
		padding-left: 30px; /* 留出图标的空间 */

	}
	.password-input {
		width: 80%;
		padding: 20rpx;
		border: 1rpx solid #000000;
		border-radius: 15px;
		background-image: url('/static/img/lock.png');
		background-position: 10px center; /* 调整图标的位置 */
		background-repeat: no-repeat;
		padding-left: 30px; /* 留出图标的空间 */
	
	}
	
	

	.btn-login {
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

	
	
	.btn-login,.footer{
		margin-bottom: 20px; /* 添加下边距 */
	}

	

	.login {
		
		background-size: contain;
	}
</style>