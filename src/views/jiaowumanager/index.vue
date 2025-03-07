<template>
	<div>
		<el-row>
			<!-- 操作栏 -->
			<el-col :span="24">
				<el-card class="box-card">

					<el-button type="primary" size="small" icon="el-icon-plus" class="button" @click="handleCreate()"
						plain>新增教学班</el-button>
				</el-card>
			</el-col>
			<!-- 数据列表部分 -->
			<el-card class="box-card data">
				<el-table :data="teachingclasses" style="width: 100%" size="small">
					<el-table-column type="index" label="序号" width="70" align="center" >
						<template slot-scope="scope">
						{{ scope.$index + 1 }}
						</template>
					</el-table-column>
					<el-table-column prop="id" label="教学班号"  sortable>
					</el-table-column>
					<el-table-column prop="course" label="课程名称">
					</el-table-column>
					<el-table-column prop="teacherId" label="授课教师">
					</el-table-column>
					<el-table-column prop="total" label="教学班总人数">
					</el-table-column>
					<el-table-column prop="sestem" label="开课学期">
					</el-table-column>
					<el-table-column label="操作" align="center" width="180px">
						<template slot-scope="scope">
							<el-button type="primary" size="small" icon="el-icon-edit" class="button" @click="handleUpdate(scope.row)"  plain>编辑
							</el-button>
							<el-button type="danger" size="small" icon="el-icon-delete" class="button" @click="handleDelete(scope.row)" plain>删除
							</el-button>
						</template>
					</el-table-column>
				</el-table>
			</el-card>
		</el-row>
		<!--删除确认弹框-->
		<div class="delete-form">
			<el-dialog title="确定要删除该课程吗？" :visible.sync="dialogFormVisibleDelete" @close='closeDialogDelete'>
				<div slot="footer" class="dialog-footer">
					<el-button @click="closeDelete()">取 消</el-button>
					<el-button type="primary" @click="deleteTc()">确 定</el-button>
				</div>
			</el-dialog>
		</div>
		<!-- 添加弹框插件 -->
		<div class="add-form">
			<el-dialog title="新增教学班" :visible.sync="dialogFormVisible">
				<el-row>
					<el-col :span="24">
						<el-form :model="formData" :rules="rules" ref="formData">
							<el-form-item label="课程名称" prop="course" label-position="right" label-width="100px">
							<el-select v-model="formData.course" placeholder="请选择课程" clearable>
										<el-option 
											v-for="item in courses" 
											:key="item.name" 
											:label="item.name" 
											:value="item.name">
										</el-option>
									</el-select>
							</el-form-item>
							<el-form-item label="开课学期" prop="sestem" label-position="right" label-width="100px">
								<el-select v-model="formData.sestem" placeholder="请选择开课学期" clearable>
									<el-option label="2024秋" value="2024秋"></el-option>
									<el-option label="2025春" value="2025春"></el-option>
								</el-select>
							</el-form-item>
						</el-form>
					</el-col>

				</el-row>

				<div slot="footer" class="dialog-footer">
					<el-button @click="dialogFormVisible = false">取 消</el-button>
					<el-button type="primary" @click="addTc()">确 定</el-button>
				</div>
			</el-dialog>
		</div>
		
		<!-- 编辑修改框 -->
		<div class="update-form">
			<el-dialog title="修改信息" :visible.sync="dialogFormVisibleEdit">
				<el-row>
					<el-col :span="24">
						<el-form :model="currentTc" :rules="rules" ref="teachingclasses">
							<el-form-item label="教学班号" prop="teachingclass" label-position="right" label-width="100px">
								<el-input v-model="currentTc.id" autocomplete="off" disabled></el-input>
							</el-form-item>
							
							<el-form-item label="课程名称" prop="course" label-position="right" label-width="100px">
								<el-input v-model="currentTc.course" disabled></el-input>
							</el-form-item>
							<el-form-item label="教授教师" prop="teacher" label-position="right" label-width="100px">
								<el-input v-model="currentTc.teacherId"></el-input>
							</el-form-item>
							
							<el-form-item label="开课学期" prop="sestem" label-position="right" label-width="100px">
								<el-select v-model="currentTc.sestem" placeholder="请选择开课学期" clearable>
									<el-option label="2024秋" value="2024秋"></el-option>
									<el-option label="2025春" value="2025春"></el-option>
								</el-select>
							</el-form-item>
						</el-form>
					</el-col>
		
				</el-row>
		
				<div slot="footer" class="dialog-footer">
					<el-button @click="dialogFormVisibleEdit = false">取 消</el-button>
					<el-button type="primary" @click="updateTc()">确 定</el-button>
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
				teachingclasses:[],
				dialogFormVisible: false,
				dialogFormVisibleEdit:false,
				namesMap:[],
				currentTc:{},
				courses:[],
				
				/* 表单数据 */
				formData: {
					
				},
				/*数据校验 */
				rules: {
					
					course: [{
						required: true,
						message: '请选择课程名称',
						trigger: 'blur'
					}],
					teacher: [{
						required: true,
						message: '请输入授课教师',
						trigger: 'blur'
					}],
					sestem: [{
						required: true,
						message: '请选择上课学期',
						trigger: 'blur'
					}],

				}
			}
		},
		created() {
			this.fetchCourses();
			
		},
		methods: {
			//读入教学班
			async fetchCourses() {
				try {
				const response = await axios.get('/findAllTeachingclass'); // 假设你使用的是 axios
				
				if (response.data.code === 200) {
					this.teachingclasses = response.data.data;
					await this.getAllTeacherNames();
				} else {
					this.$message.error('加载教学班失败');
				}
				} catch (error) {
				console.error('获取教学班列表失败:', error);
				this.$message.error('获取教学班列表失败');
				}
			},
			//获取所有教师姓名
			async getAllTeacherNames() {
				try {
					const response = await axios.get('/selectAllName');
					if (response.data.code === 200) {
						this.namesMap = response.data.data; // 这是您从后端获得的 Map
						this.teachingclasses.forEach(classItem => {
						classItem.teacherId = this.namesMap[classItem.teacherId] || '未知教师';
						});
					} else {
						this.$message.error('加载教师名称失败');
					}
				} catch (error) {
					console.error('获取教师名称失败:', error);
					this.$message.error('获取教师名称失败');
				}
			},
			
			//获取所有课程名称
			async getAllCourseNames() {
				try {
					const response = await axios.get('/selectCourse');
					if (response.data.code === 200) {
						this.courses = response.data.data; // 这是您从后端获得的 Map
					} else {
						this.$message.error('加载课程名称失败');
					}
				} catch (error) {
					console.error('获取课程名称失败:', error);
					this.$message.error('获取课程名称失败');
				}
			},
			//添加教学班
			async addTc(){
				try {
					
					const response = await axios.post(`/insertTc?course=${this.formData.course}&sestem=${this.formData.sestem}`);
					if (response.data.code === 200) {
						this.courses = response.data.data; // 这是您从后端获得的 Map
					} else {
						this.$message.error('添加教学班失败');
					}
				} catch (error) {
					console.error('添加教学班失败:', error);
					this.$message.error('添加教学班失败');
				}
				
				this.formData={};
				this.fetchCourses();
				this.dialogFormVisible = false;
			},
			

			/*删除窗口*/
			handleDelete(tc){
				this.currentTc = { ...tc};
				this.dialogFormVisibleDelete = true;
			},
			closeDelete(){
				this.dialogFormVisibleDelete = false;
				this.currentTc={};
			},
			
			async deleteTc(){
				try {
				const response = await axios.delete(`/deleteTc?id=${this.currentTc.id}`); // 假设你使用的是 axios
				if (response.data.code === 200) {
					this.$message.success('删除成功');
				} else {
					this.$message.error('删除失败');
				}
				} catch (error) {
				console.error('删除失败:', error);
				this.$message.error('删除失败');
				}
				
				this.currentTc={};
				this.fetchCourses();
				this.dialogFormVisibleDelete = false;
			},
			

			/* 弹出添加窗口 */
			handleCreate() {
				this.dialogFormVisible = true;
				this.getAllCourseNames();
			},
			/* 弹出修改窗口 */
			handleUpdate(teachingclass) {
				this.currentTc={...teachingclass};
				this.dialogFormVisibleEdit = true;
			},
			
			async updateTc(){
				try {
					let TC=this.currentTc;
					const reversenamesMap = Object.entries(this.namesMap).reduce((acc, [name, id]) => {
						acc[id] = name;
						return acc;
					}, {});
					const teacherName=reversenamesMap[TC.teacherId];
					if(!teacherName){
						this.$message.error('教师不存在');
						return;
					}
					TC.teacherId=teacherName;
					console.log(TC);
					const response = await axios.post(`/updateTc?id=${TC.id}&teacherId=${TC.teacherId}&sestem=${TC.sestem}`);
					if (response.data.code === 200) {
						this.$message.success('更新成功');
					} else {
						this.$message.error('更新失败');
					}
				} catch (error) {
					console.error('更新失败:', error);
					this.$message.error('更新失败');
				}
				this.fetchCourses();
				this.dialogFormVisibleEdit = false
			},
		},
		
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
	}

	/* 按钮样式 */
	.button {
		margin-top: 10px;
	}


	/* 设置表格行间距 */
	/deep/.el-table td,
	.el-table th {
		padding: 5px 0 !important;
	}

	/* 分页插件样式 */
	.el-pagination {
		margin: 10px;
		float: right;
	}
</style>