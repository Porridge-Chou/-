<template>
	<div class="index">
		<div class="search-bar-container">
			<div class="search-bar">
				<input type="text" v-model="searchKeyword" placeholder="请输入关键字" @confirm="onSearch" 
					style="height:2.4825rem"
				/>
				<button @click="onSearch">搜索</button>
			</div>
			<tui-tabs class="my-tabs" :tabs="tabs" :currentTab="currentTab" :is-slider="true" :height="90" :scale="1.2"
				:sliderRadius="50" :sliderBgColor="'#ffaa7a'" :color="'#272822'" :selected-color="'#ffaa7a'"
				:slider-width="89" :size="32" itemWidth="30%" @change="change"></tui-tabs>

		</div>

		<!-- 孩子信息列表 -->
		<div class="child-list-container">

			<div v-for="child in childData" :key="child.id" class="child-info">
				<div class="cc">
					<div class="container">
						<div class="child-photo-container">
							<img :src="child.image" alt="孩子照片" class="child-photo" />
						</div>
						<div class="child-details">
							<div class="child-info-item label-name">
								<label>姓名:</label>
								<span>{{ child.name }}</span>
							</div>
							<div class="child-info-item label-age">
								<label>年龄:</label>
								<span>{{ child.age }}岁</span>
							</div>
							<div class="child-info-item label-gender">
								<label>性别:</label>
								<span>{{ child.gender }}</span>
							</div>
							<div class="child-info-item">
								<label>地区:</label>
								<span>{{ child.address }}</span>
							</div>
						</div>
					</div>
					<div class="buttonAreaHorizontal">
						<div class="contact-volunteer-button" @click="contactVolunteer(child.id)">联系志愿者</div>
						<div v-if="child.isDonated" class="donate-button" @click="continueDonation(child.id)">继续捐助
						</div>
						<div class="info-button" @click="getInfo(child)">查看详情</div>
					</div>





				</div>

			</div>
		</div>
	</div>
</template>

<script>
	import tuiTabs from "@/components/thorui/tui-tabs/tui-tabs.vue"
	import tuiIcon from "@/components/thorui/tui-icon/tui-icon.vue"
	import tuiKeyboardInput from "@/components/thorui/tui-keyboard-input/tui-keyboard-input.vue"
	export default {
		components: {
			tuiKeyboardInput,
			tuiTabs,
			tuiIcon
		},
		data() {
			return {
				currentTab: 0,
				tabs: [{
					name: "全部关注"
				}, {
					name: "已经捐助"
				}, {
					name: "还未捐助"
				}],
				vId: 0,
				donaterId: 0,
				allChild: [],
				donatedChild: [],
				noDonatedChild: [],
				searchKeyword: '', // 搜索关键字
				categories: ['全部', '已经捐助', '还未捐助'], // 类型
				selectedCategoryIndex: 0, // 索引
				childData: [],

				filteredItems: [],
				item: {
					id: '',
					image: '',
					name: '',
					gender: '',
					age: '',
					address: '',
					tips: [],
					isDonated: true // 这里是默认值，您可以根据实际情况设置为true或false
				}

			};
		},
		async mounted() {
			try {
				this.donaterId = uni.getStorageSync('id');
				if (donaterId) {

					console.log(donaterId);
				} else {
					console.log("空值")
				}
				console.log("成功读取缓存")
			} catch (e) {
				console.log("读取缓存失败")
			}
			await this.fetchData(); // 使用 await 确保 fetchData 完成
			this.childData = this.allChild; // 设置 childData
		},

		methods: {
			switchTab(index) {
				if (this.currentTab == index) {
					return
				}


				this.tabIndex = index;
				if (this.tabIndex == 0) {
					console.log("进来了")
					this.childData = [...this.allChild];
				} else if (this.tabIndex == 1) {
					console.log("进来了")
					this.childData = [...this.donatedChild];
				} else if (this.tabIndex == 2) {
					console.log("进来了")
					this.childData = [...this.noDonatedChild];
				}
				console.log(this.tabIndex)
			},
			change(e) {
				this.currentTab = e.index
				if (this.currentTab == 0) {
					console.log("进来了")
					this.childData = [...this.allChild];
				} else if (this.currentTab == 1) {
					console.log("进来了")
					this.childData = [...this.donatedChild];
				} else if (this.currentTab == 2) {
					console.log("进来了")
					this.childData = [...this.noDonatedChild];
				}
			},
			async fetchData() {
				const requestData = {
					id: this.donaterId,
					name: '',
					gender: '',
					age: '',
					address: '',
					description: '',
					head_photo: ''
				};


				// 获取所有关注的孩子
				const result = await uni.request({
					url: 'http://localhost:8085/donate/queryTotalKid', // 移除多余的斜杠
					method: 'POST',
					header: {
						'Content-Type': 'application/json',
					},
					data: requestData, // 将请求数据放在data字段中
				});

				//获取所有捐助过的孩子
				const result1 = await uni.request({
					url: 'http://localhost:8085/donate/queryDonatedKid', // 移除多余的斜杠
					method: 'POST',
					header: {
						'Content-Type': 'application/json',
					},
					data: requestData, // 将请求数据放在data字段中
				});
				//获取所有关注但是未捐助的孩子
				const result2 = await uni.request({
					url: 'http://localhost:8085/donate/queryNoDonatedKid', // 移除多余的斜杠
					method: 'POST',
					header: {
						'Content-Type': 'application/json',
					},
					data: requestData, // 将请求数据放在data字段中
				});

				// result:全部关注的孩子,result1:全部捐助的孩子,result2:全部关注但是未捐助的孩子
				var responseData = result.data;
				var responseData1 = result1.data;
				var responseData2 = result2.data;

				for (let i = 0; i < responseData1.kids.length; i++) {
					let item = {
						id: responseData1.kids[i].id,
						name: responseData1.kids[i].name,
						gender: responseData1.kids[i].gender,
						age: responseData1.kids[i].age,
						address: responseData1.kids[i].address,
						image: responseData1.kids[i].headPhoto,
						tips: JSON.parse(responseData1.kids[i].tag),
						isDonated: true,
					};
					this.donatedChild.push(item);
				}
				console.log("已经捐助孩子：" + JSON.stringify(this.donatedChild))
				for (let i = 0; i < responseData2.kids.length; i++) {
					let item = {
						id: responseData2.kids[i].id,
						name: responseData2.kids[i].name,
						gender: responseData2.kids[i].gender,
						age: responseData2.kids[i].age,
						address: responseData2.kids[i].address,
						image: responseData2.kids[i].headPhoto,
						tips: JSON.parse(responseData2.kids[i].tag),
						isDonated: false,
					};
					this.noDonatedChild.push(item);
				}
				this.allChild = [...this.donatedChild, ...this.noDonatedChild];


				console.log("未捐助孩子：" + JSON.stringify(this.noDonatedChild))

			},

			onSearchType(donaterId) {

				const searchData = {
					donaterId: donaterId,
				};
				console.log(searchData);
			},
			onSearch() {
				this.currentTab = 0
				// 获取搜索关键字
				const searchKeyword = this.searchKeyword.trim().toLowerCase();

				// 根据搜索关键字过滤孩子数据
				if (searchKeyword) {
					this.childData = this.allChild.filter(child => {
						// 比较孩子的姓名、年龄、性别和地区是否包含搜索关键字
						return (
							child.name.toLowerCase().includes(searchKeyword) ||
							child.age.toString().includes(searchKeyword) ||
							child.gender.toLowerCase().includes(searchKeyword) ||
							child.address.toLowerCase().includes(searchKeyword)
						);
					});
				} else {
					// 如果搜索关键字为空，显示所有孩子数据
					this.childData = this.allChild;
				}




			},
			onCategoryChange(event) {
				this.selectedCategoryIndex = event.detail.value;

				// 根据选择的分类筛选值更新 childData
				if (this.selectedCategoryIndex == 0) {
					console.log("进来了")
					// 当选择“全部”时，childData 赋值为 allChild
					this.childData = [...this.allChild];
					// this.childData = this.allChild;
				} else if (this.selectedCategoryIndex == 1) {
					console.log("进来了")
					// 当选择“已经捐助”时，childData 赋值为 donatedChild
					this.childData = [...this.donatedChild];
					// this.childData = this.donatedChild;
				} else if (this.selectedCategoryIndex == 2) {
					console.log("进来了")
					// 当选择“还未捐助”时，childData 赋值为 noDonatedChild
					this.childData = [...this.noDonatedChild];
					// this.childData = this.noDonatedChild;
				}
				console.log("当前类别" + this.selectedCategoryIndex)
				console.log("当前类别孩子：" + JSON.stringify(this.childData))


			},
			continueDonation(childId) {
				console.log("childId: " + childId)
				uni.navigateTo({
					url: '/pages/donate/donate?childId=' + childId
				});
			},
			async contactVolunteer(childId) {
				const requestData = {
					id: childId, // 字符串类型
					name: "", // 字符串类型
					gender: "", // 字符串类型
					age: '', // 整数类型
					address: "", // 字符串类型
					tag: "", // 字符串类型
					school: "", // 字符串类型
					grade: '', // 整数类型
					description: "", // 字符串类型
					headPhoto: "", // 字符串类型
					isSwiper: '', // 整数类型
					swiperSort: '', // 整数类型
					swiperPic: "", // 字符串类型
					organizationId: "", // 字符串类型
					mustTask: "", // 字符串类型
					optTask: "", // 字符串类型
					lineChart: "", // 字符串类型
					points: '', // 整数类型


				};
				const result = await uni.request({
					url: 'http://localhost:8085/child/queryVolunteer',
					data: requestData,
					method: 'POST',
					header: {
						'Content-Type': 'application/json',
					}
				})
				console.log('响应:', result);

				this.vId = result.data.vid;
				console.log(this.vId)


				uni.navigateTo({
					url: '/pages/chat/chat' + "?fromId=" + this.donaterId + "&toId=" + this.vId,
				})
			},
			getInfo(child) {
				console.log(this.child)
				const modifiedItem = {
					id: child.id,
					image: child.image,
					name: child.name,
					category: child.address,
					tips: child.tips
				};

				uni.navigateTo({
					url: '/pages/ItemDetails/ItemDetails?data=' + JSON.stringify(modifiedItem)
				});
			}


		}
	};
</script>

<!-- 孩子信息展示样式 -->
<style lang="scss">
	.child-info {
		background-color: #f2f2f2;
		margin: 0px 0;
		padding: 10px;
		border: 1px;
		border-radius: 8px;
		display: flex;
		flex-direction: column;
	}

	.child-photo-container {
		width: 160px;
		height: 130px;
		display: flex;
		align-items: flex-start;
		/* 调整对齐方式为起始位置 */
		justify-content: flex-start;
		/* 调整对齐方式为起始位置 */
	}

	.child-photo {
		width: 300rpx;
		height: 300rpx;
	}

	.child-details {
		width: 300rpx;
		margin-left: 20rpx;
		flex: 1;
		padding-left: 10px;
		display: flex;
		flex-direction: column;
		align-items: flex-start;
		/* 调整对齐方式为起始位置 */
		align-content: flex-start;
		/* 调整对齐方式为起始位置 */
		justify-content: space-between;
		/* 垂直方向上均匀分布元素 */
		height: 100%;
		/* 让 .child-details 高度占满父容器 */
	}

	.buttonAreaHorizontal {
		display: flex;
		justify-content: flex-start;
		/* 左对齐 */
		margin-top: -5px;
		margin-left: 10rpx;
		/* 调整上边距 */
	}


	.contact-volunteer-button,
	.donate-button,
	.info-button {
		font-size: 14px;
		padding: 8px 10px;
		margin-right: 10px;
		margin-top: 10px;
		border-radius: 10px;
		white-space: nowrap;
		color: white;
	}


	.child-info-item {
		margin-bottom: 8px;
		/* 信息项之间的垂直间距 */
		display: flex;
		/* 使用 Flex 布局使标签和内容水平排列 */
		align-items: center;
		/* 垂直居中 */
		font-size: 16px;
		font-weight: bold;
	}

	.child-info-item.label-name {
		margin-bottom: 23px;
		/* 设置姓名标签与下一项之间的垂直间距 */
	}

	.child-info-item.label-age {
		margin-bottom: 23px;
		/* 设置年龄标签与下一项之间的垂直间距 */
	}

	.child-info-item.label-gender {
		margin-bottom: 23px;
		/* 设置姓名标签与下一项之间的垂直间距 */
	}

	.child-info-item label {
		font-weight: bold;
		/* 加粗标签文字 */
		margin-right: 5px;
		/* 标签和内容之间的水平间距 */
	}
</style>
<style lang="scss">
	.search-bar-container {
		display: flex;
		flex-direction: column;
		padding: 10px;
		background-color: #ffffff;
		box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
		border-radius: 8px;
	}

	.search-bar {
		display: flex;
		border-radius: 15px;
		overflow: hidden;
		height: 45px;
	}

	.search-bar input {
		height: 45rpx;
		flex: 1;
		padding: 10px;
		border: none;
		outline: none;
		background-color: #f4f6fa;
	}

	.search-bar button {
		height: 80rpx;
		padding: 0.2px 15px;
		border: none;
		background-color: #fa7f4b;
		color: white;
		cursor: pointer;
		border-radius: 0 10px 10px 0;
	}

	.search-bar button:hover {
		background-color: #1669c1;
	}

	.filter-bar {
		margin-top: 2px;
		text-align: center;
	}

	.filter-bar view {
		background-color: #e0e0e0;
		padding: 8px 16px;
		display: inline-block;
		cursor: pointer;
	}

	filter-bar picker {
		border: none;
		padding: 2px;
		-webkit-appearance: none;
		appearance: none;
		background-color: transparent;
	}


	.child-info-item {
		margin-bottom: 5px;
		display: flex;
		align-items: center;
	}

	.child-info-item label {
		font-weight: bold;
		margin-right: 5px;
	}

	#tab {
		width: 100%;
		display: flex;


	}

	.tabName {
		text-align: center;
		width: 33%;
		display: inline-block;
		height: 80rpx;
		line-height: 80rpx;
		white-space: nowrap;
		margin-top: 20px;
	}

	.tabName_text {
		display: inline-block;
		width: 100%;
		height: 100%;
	}

	.active_text {
		background-color: #ff0000;
		color: #FFFFFF;
	}

	#tabContent {
		width: 100%;
	}

	.contact-volunteer-button {
		background-color: #f5a444;
		border-radius: 10px;
		margin-right: 10px;
		margin-bottom: 10px;
		/* 设置右侧外边距 */
	}

	.donate-button {
		background-color: #f5a444;
		border-radius: 10px;
		margin-right: 10px;
		margin-bottom: 10px;
		/* 设置右侧外边距 */
	}

	.info-button {
		background-color: #ff6c4b;
		border-radius: 10px;
		margin-right: 10px;
	}



	.contact-volunteer-button,
	.donate-button,
	.info-button {
		margin-bottom: 10px;
		border-radius: 15px;
	}

	.ccc {
		display: flex;
		flex-direction: row;
		background-color: white;
		box-shadow: 2rpx 6rpx 3rpx #EAF0F0;
	}

	.container {
		margin-left: 15rpx;
		margin-top: 25rpx;
		display: flex;
		flex-direction: row;


	}

	.child-list-container {
		background-color: #f3f4f5;
	}

	.cc {
		margin-top: 10rpx;
		margin-bottom: 10rpx;
		margin-left: 20rpx;
		margin-right: 20rpx;
		background-color: white;
		box-shadow: 2rpx 6rpx 3rpx #EAF0F0;
	}
</style>