(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2686b73e"],{1346:function(t,e,l){"use strict";l("e4e2")},ab43:function(t,e,l){"use strict";l.r(e);var a=function(){var t=this,e=t._self._c;return e("div",[e("el-row",[e("el-col",{attrs:{span:24}},[e("el-card",{staticClass:"box-card"},[e("el-input",{staticClass:"filter-input",attrs:{placeholder:"请输入通知标题",size:"small"},model:{value:t.input,callback:function(e){t.input=e},expression:"input"}}),e("el-input",{staticClass:"filter-input",attrs:{placeholder:"请输入时间",size:"small"},model:{value:t.input,callback:function(e){t.input=e},expression:"input"}}),e("el-button",{attrs:{type:"primary",icon:"el-icon-search",size:"small",plain:""}},[t._v("搜索")]),e("el-button",{attrs:{type:"primary",icon:"el-icon-refresh-right",size:"small"}},[t._v("重置")]),e("br"),e("el-button",{staticClass:"button",attrs:{type:"primary",size:"small",icon:"el-icon-plus",plain:""},on:{click:function(e){return t.handleCreate()}}},[t._v("新增")]),e("el-button",{staticClass:"button",attrs:{type:"success",size:"small",icon:"el-icon-download",plain:""}},[t._v("导入")]),e("el-button",{staticClass:"button",attrs:{type:"warning",size:"small",icon:"el-icon-upload2",plain:""}},[t._v("导出")]),e("el-button",{staticClass:"button",attrs:{type:"danger",size:"small",icon:"el-icon-delete",plain:""}},[t._v("批量删除")])],1)],1),e("el-card",{staticClass:"box-card data"},[e("el-table",{staticStyle:{width:"100%"},attrs:{data:t.tableData,size:"small"}},[e("el-table-column",{attrs:{type:"selection",width:"55"}}),e("el-table-column",{attrs:{prop:"id",label:"序号",width:"80"}}),e("el-table-column",{attrs:{prop:"title",label:"通知标题",width:"180"}}),e("el-table-column",{attrs:{prop:"content",label:"通知内容"}}),e("el-table-column",{attrs:{label:"操作",align:"center",width:"180px"}},[[e("el-button",{staticClass:"button",attrs:{type:"primary",size:"small",icon:"el-icon-edit",plain:""},on:{click:function(e){return t.handleUpdate()}}},[t._v("编辑 ")]),e("el-button",{staticClass:"button",attrs:{type:"danger",size:"small",icon:"el-icon-delete",plain:""}},[t._v("删除 ")])]],2)],1),e("el-pagination",{attrs:{"current-page":t.currentPage4,"page-size":100,layout:"total, prev, pager, next, jumper",total:100},on:{"size-change":t.handleSizeChange,"current-change":t.handleCurrentChange}})],1)],1),e("div",{staticClass:"add-form"},[e("el-dialog",{attrs:{title:"新增通知",visible:t.dialogFormVisible},on:{"update:visible":function(e){t.dialogFormVisible=e}}},[e("el-row",[e("el-col",{attrs:{span:24}},[e("el-form",{ref:"formData",attrs:{model:t.formData,rules:t.rules}},[e("el-form-item",{attrs:{label:"标题",prop:"college","label-position":"right","label-width":"100px"}},[e("el-input",{attrs:{autocomplete:"off"},model:{value:t.formData.college,callback:function(e){t.$set(t.formData,"college",e)},expression:"formData.college"}})],1),e("el-form-item",{attrs:{label:"通知内容","label-position":"right","label-width":"100px"}},[e("el-input",{attrs:{type:"textarea"},model:{value:t.formData.profile,callback:function(e){t.$set(t.formData,"profile",e)},expression:"formData.profile"}})],1)],1)],1)],1),e("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[e("el-button",{on:{click:function(e){t.dialogFormVisible=!1}}},[t._v("取 消")]),e("el-button",{attrs:{type:"primary"},on:{click:function(e){t.dialogFormVisible=!1}}},[t._v("确 定")])],1)],1)],1),e("div",{staticClass:"update-form"},[e("el-dialog",{attrs:{title:"修改信息",visible:t.dialogFormVisibleEdit},on:{"update:visible":function(e){t.dialogFormVisibleEdit=e}}},[e("el-row",[e("el-col",{attrs:{span:24}},[e("el-form",{ref:"formData",attrs:{model:t.formData,rules:t.rules}},[e("el-form-item",{attrs:{label:"学院名称",prop:"college","label-position":"right","label-width":"100px"}},[e("el-input",{attrs:{autocomplete:"off"},model:{value:t.formData.college,callback:function(e){t.$set(t.formData,"college",e)},expression:"formData.college"}})],1),e("el-form-item",{attrs:{label:"学院地址",prop:"address","label-position":"right","label-width":"100px"}},[e("el-input",{model:{value:t.formData.address,callback:function(e){t.$set(t.formData,"address",e)},expression:"formData.address"}})],1),e("el-form-item",{attrs:{label:"联系电话",prop:"telephone","label-position":"right","label-width":"100px"}},[e("el-input",{model:{value:t.formData.address,callback:function(e){t.$set(t.formData,"address",e)},expression:"formData.address"}})],1),e("el-form-item",{attrs:{label:"学院简介","label-position":"right","label-width":"100px"}},[e("el-input",{attrs:{type:"textarea"},model:{value:t.formData.profile,callback:function(e){t.$set(t.formData,"profile",e)},expression:"formData.profile"}})],1)],1)],1)],1),e("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[e("el-button",{on:{click:function(e){t.dialogFormVisible=!1}}},[t._v("取 消")]),e("el-button",{attrs:{type:"primary"},on:{click:function(e){t.dialogFormVisible=!1}}},[t._v("确 定")])],1)],1)],1)],1)},i=[],o={data(){return{tableData:[{id:1,title:"关于新学期开学新生报到通知",content:"新学期新生报到通知，关于新学期开学新生报到通知关于新学期开学新生报到通知",date:"2024-8-23"},{id:2,title:"关于新学期开学新生报到通知",content:"新学期新生报到通知，关于新学期开学新生报到通知关于新学期开学新生报到通知",date:"2024-8-23"}],dialogFormVisible:!1,dialogFormVisibleEdit:!1,formData:{college:"",telephone:"",address:"",profile:""},rules:{college:[{required:!0,message:"请输入学院名称",trigger:"blur"}],telephone:[{required:!0,message:"请输入联系号码",trigger:"blur"}],address:[{required:!0,message:"请输入学院地址",trigger:"blur"}]}}},methods:{handleCreate(){this.dialogFormVisible=!0},handleUpdate(){this.dialogFormVisibleEdit=!0}}},r=o,s=(l("1346"),l("2877")),n=Object(s["a"])(r,a,i,!1,null,"3108569d",null);e["default"]=n.exports},e4e2:function(t,e,l){}}]);
//# sourceMappingURL=chunk-2686b73e.5e550651.js.map