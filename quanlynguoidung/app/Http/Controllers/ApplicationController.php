<?php
namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Application;

class ApplicationController extends Controller
{
    public function index(Request $request)
    {
        $search = $request->input('search');
        $applications = Application::when($search, function ($query, $search) {
            $query->where('code', 'like', "%$search%")
                  ->orWhere('user_name', 'like', "%$search%");
        })->get();

        return view('applications.index', compact('applications'));
    }
    public function edit($id)
{
    $application = Application::findOrFail($id);
    return view('applications.edit', compact('application'));
}
public function updateStatus(Request $request, $id)
{
    $application = Application::findOrFail($id);
    $application->status = $request->status;
    $application->save();

    return redirect()->route('applications.index')->with('success', 'Cập nhật trạng thái thành công.');
}

}
