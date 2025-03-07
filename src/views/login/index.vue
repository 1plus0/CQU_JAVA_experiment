<template>
	<div class="container">
		<div class="title">
			<span>成绩管理系统</span>
		</div>
		<div class="main">
			<el-card class="box-card">
				<div class="text item">
					<p>用户登录</p>

					<el-form ref="formData" :model="formData" :rules="rules">
						<el-form-item prop="usertype">
								<el-select v-model="formData.usertype" placeholder="请选择账号类型" clearable>
									<el-option label="教务" value="jiaowu"></el-option>
									<el-option label="教师" value="teacher"></el-option>
									<el-option label="学生" value="student"></el-option>
								</el-select>
							</el-form-item>
						<el-form-item prop="username">
							<el-input placeholder="请输入工号/学号" prefix-icon="el-icon-user" v-model="formData.username">
							</el-input>
						</el-form-item>
						<el-form-item prop="password">
							<el-input placeholder="请输入密码" type="password" prefix-icon="el-icon-unlock"
								v-model="formData.password" show-password="true">

							</el-input>
						</el-form-item>
						
					</el-form>
					<el-button type="primary" @click="login('formData')">登&nbsp;录</el-button>
					<el-link type="primary">忘记密码?</el-link>
				</div>
			</el-card>
		</div>
	</div>
</template>

<script>
	import axios from "axios";
	
	export default {
		data() {
			return {
				formData: {
							usertype: '', // 账号类型
							username: '', // 工号/学号
							password: ''  // 密码
						},
				/* 定义表单校验规则 */
				rules: {
					username: [{
						required: true,
						message: '请输入学号/工号',
						trigger: 'blur'
					}, {
						min: 1,
						max: 8,
						trigger: 'blur'
					}],
					password: [{
						required: true,
						message: '请输入登录密码',
						trigger: 'blur'
					}],
					usertype:[{
						required: true,
						message: '请选择账号类型',
						trigger: 'blur'
					}]
					

				}
			}
		},
		/* 函数定义区域 */
		methods: {
			async login(formName) {
					this.$refs[formName].validate(async (valid) => {
						if (valid) {
							try {
								let response;
								switch (this.formData.usertype) {
									case 'jiaowu':
										response = await axios.get('/login/jiaowu', {
											params: {
												id: this.formData.username,
												password: this.formData.password
											}
										});
										if (response.data.code === 1001) {
											this.$message.error('账号或密码错误');
										} else if(response.data.code === 200){
											this.$message.success('登录成功');
											localStorage.setItem('user', JSON.stringify({ id: this.formData.username}));
											// 根据需要进行跳转或进一步操作
											this.$router.push('/jiaowuhome'); // 假设登录成功后跳转到首页
										}
										break;
									case 'student':
										response = await axios.get('/login/student', {
												params: {
												id: this.formData.username,
												password: this.formData.password
										}
										});
										if (response.data.code === 1001) {
											this.$message.error('账号或密码错误');
										} else if(response.data.code === 200){
											this.$message.success('登录成功');
											// 存储用户信息到本地存储
											localStorage.setItem('user', JSON.stringify({ id: this.formData.username}));
											// 根据需要进行跳转或进一步操作
											this.$router.push('/studenthome'); // 假设登录成功后跳转到首页
										}
										break;
									case 'teacher':
										response = await axios.get('/login/teacher', {
											params: {
											id: this.formData.username,
											password: this.formData.password
										}
										});
										if (response.data.code === 1001) {
											this.$message.error('账号或密码错误');
										} else if(response.data.code === 200){
											this.$message.success('登录成功');
											localStorage.setItem('user', JSON.stringify({ id: this.formData.username}));
											// 根据需要进行跳转或进一步操作
											this.$router.push('/teacherhome'); // 假设登录成功后跳转到首页
										}
									break;
								default:
									this.$message.error('请选择账号类型');
									return;
							}
									
						} catch (error) {
							console.error('登录失败:', error);
							this.$message.error('登录失败');
						}
					} else {
						console.log('表单验证失败');
						return false;
					}
					});
			}
			}
			
	}
</script>

<style scoped>
	/* logo标题部分样式 */
	.title {
		width: 100%;
		height: 60px;
		background: rgb(54, 54, 54);
	}

	.title span {
		color: #fff;
		line-height: 60px;
		display: inline-block;
	}

	.title span:first-child {
		margin-left: 20px;
		font-size: 24px;
		font-weight: 600;
	}

	.title span:last-child {
		margin-left: 30px;
		font-size: 20px;
	}

	.main {
		position: relative;
	}

	/* 轮播图部分 */
	.el-carousel__item h3 {
		color: #475669;
		font-size: 14px;
		opacity: 0.75;
		line-height: 150px;
		margin: 0;
	}

	.el-carousel__item:nth-child(2n) {
		background-color: #99a9bf;
	}

	.el-carousel__item:nth-child(2n+1) {
		background-color: #d3dce6;
	}

	.box-card {
		position: absolute;
		top:100%;
		left: 50%;
		transform: translate(-50%, 50%);
		z-index: 999;
		width: 400px;
		height: 400px;
	}

	.item p {
		margin-left: 38%;
		margin-top: 10%;
		margin-bottom: 20px;
		font-size: 18px;
	}
	.el-select{
		width: 90%;
		margin-left: 5%;
	}
	.el-input {
		width: 90%;
		margin-left: 5%;
		

	}

	.el-checkbox {
		margin-left: 20px;

	}

	/* 复选框文字 */
	.el-checkbox__label {
		font-size: 13px;
	}

	/* 登录按钮 */
	.el-button {
		width: 90%;
		margin-left: 20px;
		margin-top: 10px;
	}

	.el-form-item {
		margin-bottom: 18px;
	}

	/* 设置提示 */
	.el-form-item__error {
		margin-left: 20px;
	}

	/* 忘记密码链接 */
	.el-link {
		margin-top: 10px;
		margin-left: 20px;
		font-size: 13px;
	}
	/deep/.el-form-item__error{
		margin-left: 15px;
	}
</style>
