<?php

namespace App\Http\Controllers;

use App\Models\Violation;
use Illuminate\Http\Request;

class ViolationController extends Controller
{
    public function index()
    {
        $violations = Violation::all();
        return view('violations.index', compact('violations'));
    }
    public function show($id)
{
    $violation = Violation::findOrFail($id);
    return view('violations.show', compact('violation'));
}
public function destroy($id)
{
    $violation = Violation::findOrFail($id);
    $violation->delete();

    return redirect()->route('violations.index')->with('success', 'Đã xóa vi phạm thành công.');
}
public function update(Request $request, $id)
{
    $violation = Violation::findOrFail($id);
    $violation->status = $request->input('status');
    $violation->save();

    return redirect()->route('violations.index')->with('success', 'Đã cập nhật trạng thái thành công!');
}


}
