<template>
	<view>
		<uni-list :border="false">
			<uni-list-chat :avatar-circle="true" title="刘志程" avatar="/static/img/contact.jpg"
				note="够素雅吗？还是要改改？" time="06.11 16:08" :clickable="false" clickable
				v-for="(itme,index) of 10" :key="index" />
		</uni-list>
		<uni-icons type="star-filled" color="#000000" size="18"></uni-icons>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				communication:[]

			};
		},
		methods:{
			async fetchData(){
				const requestData = {
					id: '56d0b7d81ba845e7a46c057a71895511',
					name: '',
					gender: '',
					age: '', 
					address: '',
					description: '',
					head_photo: ''
				};
				const result =await uni.request({
					url: 'http://localhost:8080/donate/queryTotalKid',
					method:'POST',
					header: {
						'Content-Type': 'application/json',
					},
					data:this.requestData,
					
				});
				var responseData =result.data;
				for(let i=0;i<responseData.length;i++){
					let item={

						
					}
				}
				
				
				
			},

		}
	}
</script>

<style lang="scss">
	.custom-chat-item {
		background-color: #ff0000;
		/* 设置背景颜色为淡红色 */
	}
</style>