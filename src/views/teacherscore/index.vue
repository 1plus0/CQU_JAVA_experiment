<template>
	<div>
		<el-row>
			
			<!-- 操作栏 -->
			<el-col :span="24">
				<el-card class="box-card">
					<el-card>
						<el-select v-model="selectedClass" placeholder="请选择教学班" clearable>
									<el-option 
										v-for="item in teachingClasses" 
										:key="item.id" 
										:label="item.id" 
										:value="item.id">
									</el-option>
								</el-select>
						<el-button type="primary" size="small" class="button" plain @click="searchTc()">确认</el-button>
					</el-card>
				

					<el-input v-model="studentId" placeholder="请输入学生学号" size="small" class="filter-input"></el-input>
					<el-button type="primary" icon="el-icon-search" size="small" plain @click="search()">搜索</el-button>
					<el-input v-model="studentName" placeholder="请输入学生姓名" size="small" class="filter-input"></el-input>
					<el-button type="primary" icon="el-icon-search" size="small" plain @click="searchByName()">搜索</el-button>
					<el-button type="primary" icon="el-icon-refresh-right" size="small" @click="reset()" class="reset">重置</el-button><br />
							

						
					</el-card>
				
			</el-col>
			<!-- 数据列表部分 -->
			<el-card class="box-card data">
				<el-button type="primary" size="small" icon="el-icon-download" class="button1" @click="handleCreate()"
							plain>批量登记成绩</el-button>
				<el-table :data="tableData" style="width: 100%" size="small" @selection-change="handleSelectionChange">
					<el-table-column type="selection" width="55"></el-table-column>
					<el-table-column type="index" label="序号" width="70" align="center" >
						<template slot-scope="scope">
						{{ scope.$index + 1 }}
						</template>
					</el-table-column>
					<el-table-column prop="studentId" label="学号" width="100" sortable>
					</el-table-column>
					<el-table-column prop="studentName" label="姓名" width="100">
					</el-table-column>
					<el-table-column prop="dailyscore" label="平时成绩">
					</el-table-column>
					<el-table-column prop="midtermscore" label="期中成绩">
					</el-table-column>
					<el-table-column prop="experimentscore" label="实验成绩">
					</el-table-column>
					<el-table-column prop="finalscore" label="期末成绩">
					</el-table-column>
					<el-table-column prop="totalscore" label="总成绩" sortable>
					</el-table-column>
					<el-table-column label="操作" align="center" width="100px">
						<template slot-scope="scope">
							<el-button type="primary" size="small" icon="el-icon-edit" class="button" @click="handleUpdate(scope.row)"  plain>编辑
							</el-button>
						</template>
					</el-table-column>
				</el-table>
			</el-card>
		</el-row>
		<!-- 批量录入成绩插件 -->
		<div class="add-form">
			<el-dialog title="批量登记成绩" :visible.sync="dialogFormVisible">
				<el-row>
					<el-col :span="24">
						
						<el-form :rules="rules" ref="">
							<el-form-item label="成绩" prop="scoreType" label-position="right" label-width="100px">
								<el-select v-model="scoreType" placeholder="请选择登入成绩类型" clearable>
									<el-option label="平时成绩" value="dailyscore"></el-option>
									<el-option label="期中成绩" value="midtermscore"></el-option>
									<el-option label="实验成绩" value="experimentscore"></el-option>
									<el-option label="期末成绩" value="finalscore"></el-option>
								</el-select>
							</el-form-item>
							<el-form-item label="成绩" prop="course" label-position="right" label-width="100px">
								<el-input v-model="inputScore"></el-input>
							</el-form-item>
						</el-form>
					</el-col>
				</el-row>
				<div slot="footer" class="dialog-footer">
					<el-button @click="dialogFormVisible = false">取 消</el-button>
					<el-button type="primary" @click="editScores()">确 定</el-button>
				</div>
			</el-dialog>
		</div>
		<!-- 编辑修改框 -->
		<div class="update-form">
			<el-dialog title="修改信息" :visible.sync="dialogFormVisibleEdit">
				<el-row>
					<el-col :span="24">
						<el-form :model="currentScore" :rules="rules" ref="tableData">
							<el-form-item label="学号" prop="id" label-position="right" label-width="100px">
								<el-input v-model="currentScore.studentId" autocomplete="off" disabled></el-input>
							</el-form-item>
		
							<el-form-item label="姓名" prop="name" label-position="right" label-width="100px">
								<el-input v-model="currentScore.studentName" disabled></el-input>
							</el-form-item>
							<el-form-item label="平时成绩" prop="dailyscore" label-position="right" label-width="100px">
								<el-input v-model="currentScore.dailyscore"></el-input>
							</el-form-item>
							<el-form-item label="期中成绩" prop="midtermscore" label-position="right" label-width="100px">
								<el-input v-model="currentScore.midtermscore"></el-input>
							</el-form-item>
							<el-form-item label="实验成绩" prop="experimentscore" label-position="right" label-width="100px">
								<el-input v-model="currentScore.experimentscore"></el-input>
							</el-form-item>
							<el-form-item label="期末成绩" prop="finalscore" label-position="right" label-width="100px">
								<el-input v-model="currentScore.finalscore"></el-input>
							</el-form-item>
						</el-form>
					</el-col>
		
				</el-row>
		
				<div slot="footer" class="dialog-footer">
					<el-button @click="dialogFormVisibleEdit = false">取 消</el-button>
					<el-button type="primary" @click="editScore()">确 定</el-button>
				</div>
			</el-dialog>
		</div>
	</div>
</template>

<script>
	import axios from "axios";
	export default {
		data() {
			return {
				tableData: [],
				teachingClasses:[],
				dialogFormVisible: false,
				dialogFormVisibleEdit:false,
				user: null,
				selectedClass:null,
				studentMap:[],
				studentId: null, // 初始化为空字符串
				studentName: null,
				currentScore:{},
				scoreType:null,
				inputScore:null,
				selectedStudents: [],
				
				
				/*数据校验 */
				rules: {

				}
			}
		},
		created() {
			const user = localStorage.getItem('user');
			if (user) {
				this.user = JSON.parse(user);
			}
			this.fetchTeachingclasses();
			this.fetchStudentMap();
			
		},
		methods: {
			async fetchTeachingclasses(){
				try {
				const params = {
					teacherId: this.user.id
					};
				const response = await axios.get('/selectTcByteacher',{params}); // 假设你使用的是 axios
				if (response.data.code === 200) {
					this.teachingClasses = response.data.data;
				} else {
					this.$message.error('加载教学班失败');
				}
				} catch (error) {
				console.error('获取教学班列表失败:', error);
				this.$message.error('获取教学班列表失败');
				}
			},
			
			async fetchStudentMap(){
				try {
					const response = await axios.get('/findStudentName');
					if (response.data.code === 200) {
					this.studentMap = response.data.data;
					} else {
					this.$message.error('信息获取失败');
					}
				} catch (error) {
					console.error('获取信息失败:', error);
					this.$message.error('获取信息失败');
				}
				
			},
			//通过学号查找
			async search() {
				try {
				const params = {
					studentId: this.studentId || null, // 如果 studentId 为空字符串或未定义，则发送 null
					teachingclassId: this.selectedClass,
					};
				const response = await axios.get('/selectScoresByIds', { params });
				if (response.data.code === 200) {
					this.tableData = response.data.data;
					this.tableData.forEach(item => {
							item.studentName = this.studentMap[item.studentId];
							
							});
						
				} else {
					this.$message.error('未搜索到成绩');
				}
				} catch (error) {
				console.error('获取成绩失败:', error);
				this.$message.error('获取成绩失败');
				}
			
				},
			//通过名字查找
			async searchByName() {
				try {
					// 创建反向映射
					const reverseStudentMap = Object.entries(this.studentMap).reduce((acc, [name, id]) => {
						acc[id] = name;
						return acc;
					}, {});
				const params = {
					studentId: reverseStudentMap[this.studentName] , // 如果 studentId 为空字符串或未定义，则发送 null
					teachingclassId: this.selectedClass,
					};
				const response = await axios.get('/selectScoresByIds', { params });
				if (response.data.code === 200) {
					this.tableData = response.data.data;
					this.tableData.forEach(item => {
							item.studentName = this.studentMap[item.studentId];
							});
						
				} else {
					this.$message.error('未搜索到成绩');
				}
				} catch (error) {
				console.error('获取成绩失败:', error);
				this.$message.error('获取成绩失败');
				}
			
				},
			//教学班成绩展示
			async searchTc() {
				try {
				const params = {
					studentId: null, // 如果 studentId 为空字符串或未定义，则发送 null
					teachingclassId: this.selectedClass || '',
					};
				const response = await axios.get('/selectScoresByIds', { params });
				if (response.data.code === 200) {
					this.tableData = response.data.data;
					this.tableData.forEach(item => {
							item.studentName = this.studentMap[item.studentId];
							
							});
						
				} else {
					this.$message.error('未搜索到成绩');
				}
				} catch (error) {
				console.error('获取成绩失败:', error);
				this.$message.error('获取成绩失败');
				}
			
				},
			reset() {
			
					// 重置搜索框和表格数据
					this.studentId = null;
					this.studentName = null;
					this.search();
				},
				
			async editScore(){
				try {
					
				const params = {
					studentId: this.currentScore.studentId, 
					teachingclassId: this.selectedClass,
					dailyscore: this.currentScore.dailyscore,
					midtermscore: this.currentScore.midtermscore,
					experimentscore:this.currentScore.experimentscore,
					finalscore:this.currentScore.finalscore,
					};
				const response = await axios.post('/updateScore', params);
				if (response.data.code === 200) {
					this.$message.success("修改成绩成功")
				} else {
					this.$message.error('修改成绩失败');
				}
				} catch (error) {
				console.error('修改成绩失败:', error);
				this.$message.error('修改成绩失败');
				}
				this.search();
				this.dialogFormVisibleEdit = false
			},
			
			async editScores(){
				// 提取选中的学生的 studentId
					const studentIds = this.selectedStudents.map(student => student.studentId);
					
					// 这里可以将 studentIds 发送到后端，进行批量更新操作
					console.log('Selected student IDs: ', studentIds);
					const json={
						property:this.scoreType,
						newValue:parseInt(this.inputScore, 10),
						teachingclassId:this.selectedClass,
						studentIds: studentIds,
					};
					console.log(json);
					try {
					const response = await axios.post("/batchUpdateScores",json);
					if (response.data.code === 200) {
						this.$message.success('录入成功');
							
					} else {
						this.$message.error('录入失败1');
					}
					} catch (error) {
					console.error('录入失败:', error);
					this.$message.error('录入失败');
					
					}
				this.search();
				this.dialogFormVisible = false
			},
			
			handleSelectionChange(val) {
					this.selectedStudents = val;
				},

			/* 弹出批量登记成绩窗口 */
			handleCreate() {
				this.dialogFormVisible = true
			},
			/* 弹出修改窗口 */
			handleUpdate(score) {
				this.currentScore={...score};
				this.dialogFormVisibleEdit = true;
			}
		}

	}
</script>

<style scoped>
	/* 操作框样式 */
	.box-card {
		margin: 5px;
	}

	/* 搜索框样式 */
	.filter-input {
		width: 200px;
		margin-right: 20px;
		margin-left: 10px;
		margin-top:10px;
	}

	/* 按钮样式 */
	.button {
		margin-top: 10px;
		margin-left: 5px;
	}
	.reset{
		margin-top: 10px;
		margin-left: 500px;
	}


	/* 设置表格行间距 */
	/deep/.el-table td,
	.el-table th {
		padding: 5px 0 !important;
	}

	
</style>