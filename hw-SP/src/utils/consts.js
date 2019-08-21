const allPlatForm = [
  {
    value: '高速纸机',
    class: [
      {key: 'A', value: '卫卷纸'},
      {key: 'B', value: '纸巾纸(软抽)'},
      {key: 'C', value: '餐巾纸'},
    ],
  },
  {
    value: '1575纸机',
    class: [
      // B:'本色纸巾纸', //
      // BW:'本色卫生纸',
      // C:'餐巾纸原纸',
      // J:'卫卷纸',
      // R:'纸巾纸(软抽)',
      // S:'手帕纸原纸',
      // W:'卫生纸原纸',
      // Z:'纸巾纸原纸',
      {key: 'W', value: '卫生纸原纸'},
      {key: 'Z', value: '纸巾纸原纸'},
      {key: 'J', value: '卫生纸原纸复卷'},
      {key: 'R', value: '纸巾纸原纸复卷'},
      {key: 'B', value: '本色纸巾纸'}

    ]
  },
  {
    value: '3900纸机',
    class: [
      {key: 'W', value: '卫生纸原纸'},
      {key: 'Z', value: '纸巾纸原纸'},
      {key: 'C', value: '餐巾纸'},
      {key: 'S', value: '手帕纸原纸'},
      {key: 'B', value: '本色纸巾纸'},
      {key: 'BW', value: '本色卫生纸'},
    ]
  },
];
const allColor = [
  {
    value: '白色',
    label: '白色',
  },
  {
    value: '本色',
    label: '本色',
  }
];

const allStatus =
  new Map([
    [10, '未提交'],
    [11, '提交OA'],
    [20, '审批中'],
    [30, '已审批'],
    [35, '不通过'],
    [40, '已过期'],
  ]);

const Const_Util = {
  allColor: allColor,
  allPlatform: allPlatForm,
  allStatus: allStatus
};


export default Const_Util
